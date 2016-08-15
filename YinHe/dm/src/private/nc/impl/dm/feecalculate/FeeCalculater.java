/**
 * 
 */
package nc.impl.dm.feecalculate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import nc.bs.dm.tariff.rule.bp.maintain.FillUpDataRule;
import nc.bs.framework.common.NCLocator;
import nc.bs.ml.NCLangResOnserver;
import nc.bs.pub.formulaparse.FormulaParse;
import nc.impl.pubapp.env.BSContext;
import nc.itf.dm.policy.IBatRangeMaintain;
import nc.itf.dm.policy.IPolicyMaintainApp;
import nc.itf.dm.tariffdef.ITariffdefMaintain;
import nc.itf.scmpub.reference.uap.bd.material.MaterialPubService;
import nc.itf.scmpub.reference.uap.org.OrgUnitPubService;
import nc.md.model.impl.MDEnum;
import nc.pubitf.dm.feecalculate.ICalculateBillView;
import nc.pubitf.dm.formula.IFormulaService;
import nc.pubitf.dm.tariff.ITariffPubManage;
import nc.vo.bd.material.MaterialVO;
import nc.vo.dm.apsettledetail.entity.ApSettleDetailVO;
import nc.vo.dm.enumeration.FallotsetFlag;
import nc.vo.dm.feecalculate.entity.FeeCalculateVO;
import nc.vo.dm.feecalculate.entity.FeeMatCalVO;
import nc.vo.dm.formula.entity.FormularVO;
import nc.vo.dm.m4804.entity.DelivBillBVO;
import nc.vo.dm.policy.entity.BatchRangeBVO;
import nc.vo.dm.policy.entity.BatchRangeVO;
import nc.vo.dm.policy.entity.FeePlcyFeeVO;
import nc.vo.dm.policy.entity.FeePlcyPrcVO;
import nc.vo.dm.policy.entity.FeePlcyVO;
import nc.vo.dm.pub.DMCalculConstant;
import nc.vo.dm.tariff.entity.FeeTariffBatVO;
import nc.vo.dm.tariff.entity.FeeTariffDetailVO;
import nc.vo.dm.tariff.entity.FeeTariffVO;
import nc.vo.dm.tariffdef.entity.FeeTariffDefBVO;
import nc.vo.dm.tariffdef.entity.FeeTariffDefVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.formulaset.FormulaParseFather;
import nc.vo.pub.formulaset.VarryVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.pub.MapList;
import nc.vo.pubapp.pattern.pub.MathTool;
import nc.vo.pubapp.scale.ScaleUtils;

/**
 * @author yuxx
 * @created at 2016-8-9,����11:17:45
 *
 */
public class FeeCalculater {

	  /**
	   * key:�۸������
	   */
	  private Map<String, FeePlcyPrcVO> prcvomap =
	      new HashMap<String, FeePlcyPrcVO>();

	  /**
	   * �������ּ��۸���۸�map
	   * key: �۸��ά��id+�۸������
	   */
	  private Map<String, UFDouble> nobatchPrcPrice = null;

	  /**
	   * �����ּ��۸���۸�map
	   * key���۸��ά��id+�����ּ���ʵ��id
	   */
	  private Map<String, UFDouble> batchPrcPrice = null;

	  /**
	   * ��λ��
	   */
	  private String curr = null;

	  /**
	   * �����ּ���Χmap
	   */
	  private Map<String, BatchRangeVO> batchRangeMap =
	      new HashMap<String, BatchRangeVO>();

	  /**
	   * ��̯����map
	   */
	  private Map<Integer, String> allotflagmap = new HashMap<Integer, String>();

	  /**
	   * �˷Ѽ���
	   * 
	   * @param vo �˷Ѽ��㽻�ӵ�
	   * @return ��������ϸ
	   */
	  public List<ApSettleDetailVO> calculate(FeeCalculateVO vo) {

	    // this.getScale(vo);
	    this.getCurr(vo);
	    // ������֯
	    String pk_org = vo.getpk_org();
	    // ���䷽ʽ
	    String csendtypeid = vo.getcsendtypeid();
	    // ������
	    String ccarrierid = vo.getccarrierid();
	    // ���Զ���
	    FeePlcyVO feeplcyvo = this.queryFeePlcy(pk_org, csendtypeid, ccarrierid);
	    // �۸����
	    FeeTariffDefVO tariffdefvo =
	        this.queryTariffDef(pk_org, csendtypeid, ccarrierid);
	    // �۸����ά��
	    List<String> fields = this.getCollectFields(tariffdefvo);
	    // �۸��ά��
	    FeeTariffVO[] tariffs = this.queryTariff(vo, tariffdefvo);
	    if(tariffs==null){
	    	tariffs = new FeeTariffVO[1];
	    	tariffs[0]= new FeeTariffVO();
	    	FeeTariffDetailVO fvo = new FeeTariffDetailVO();
	    	tariffs[0].setParentVO(fvo);
	    	
	    	tariffs[0].getParentVO().setPk_org(pk_org);
	    	tariffs[0].getParentVO().setCsendtypeid(csendtypeid);
	    	tariffs[0].getParentVO().setCcarrierid(ccarrierid);
	    	tariffs[0].getParentVO().setNprice1(UFDouble.ZERO_DBL);
	    }

	    // �۸����ϸmap,key:����ά��+ά��ֵ
	    Map<String, FeeTariffVO> tariffmap =
	        this.changeFeeTariffToMap(fields, tariffs);

	    // �����۸��������ӳ��map
	    this.creatmaps(feeplcyvo);

	    List<ApSettleDetailVO> results =
	        this.calculateFeeMat(vo, feeplcyvo, fields, tariffmap);
	    return results;
	  }

	  private void creatmaps(FeePlcyVO feeplcyvo) {
	    this.createPrcVOMap(feeplcyvo);
	    this.createAllotflagMap();
	    this.createBatchLevelMap(feeplcyvo);
	  }

	  private void getCurr(FeeCalculateVO vo) {
	    String curr = null;
	    for (ICalculateBillView view : vo.getViews()) {
	      String pk_org = view.getcsettlefinorgid();
	      String orgCurr = OrgUnitPubService.getOrgCurr(pk_org);
	      if (curr != null && !curr.equals(orgCurr)) {
	        String msg =
	            nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014002_0",
	                "04014002-0018")/*@res "�����н��������֯���Ҳ�ͬ���޷������˷Ѽ��㣡"*/;
	        ExceptionUtils.wrappBusinessException(msg);
	      }
	      if (curr == null) {
	        curr = OrgUnitPubService.getOrgCurr(pk_org);
	      }
	    }
	    if (curr != null) {
	      this.curr = curr;
	    }
	  }

	  /**
	   * ѯ���Զ���
	   * 
	   * @param pk_org ������֯
	   * @param csendtypeid ���䷽ʽ
	   * @param ccarrierid ������
	   * @return ���Զ���
	   */
	  private FeePlcyVO queryFeePlcy(String pk_org, String csendtypeid,
	      String ccarrierid) {
	    FeePlcyVO feeplcyvo = null;
	    IPolicyMaintainApp policyservice =
	        NCLocator.getInstance().lookup(IPolicyMaintainApp.class);
	    try {
	      feeplcyvo = policyservice.queryFeePlcyVO(pk_org, csendtypeid, ccarrierid);
	      if (feeplcyvo == null) {
	        String msg =
	            nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014002_0",
	                "04014002-0019")/*@res "����ʧ�ܣ�δƥ�䵽���Զ��壬���飡"*/;
	        ExceptionUtils.wrappBusinessException(msg);
	      }
	      else if (feeplcyvo.getFeePlcyFeeVO().length == 0) {
	        String msg =
	            nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014002_0",
	                "04014002-0020")/*@res "����ʧ�ܣ����Զ���δ�����������飡"*/;
	        ExceptionUtils.wrappBusinessException(msg);
	      }
	    }
	    catch (BusinessException e) {
	      ExceptionUtils.wrappException(e);
	    }
	    return feeplcyvo;
	  }

	  /**
	   * �����۸���map
	   * 
	   * @param feeplcyvo ���Զ���
	   */
	  private void createPrcVOMap(FeePlcyVO feeplcyvo) {
	    for (FeePlcyPrcVO prcvo : feeplcyvo.getFeePlcyPrcVO()) {
	      if (prcvo.getBquotedflag().booleanValue()) {
	        this.prcvomap.put(prcvo.getVitemcode(), prcvo);
	      }
	    }
	  }

	  /**
	   * ���������ּ���Χmap
	   * 
	   * @param feeplcyvo ���Զ���
	   */
	  private void createBatchLevelMap(FeePlcyVO feeplcyvo) {
	    List<String> batlevelids = new ArrayList<String>();
	    for (FeePlcyPrcVO prcvo : feeplcyvo.getFeePlcyPrcVO()) {
	      if (prcvo.getBbatprcflag().booleanValue()) {
	        batlevelids.add(prcvo.getCbatlevelid());
	      }
	    }
	    if (batlevelids.size() > 0) {
	      BatchRangeVO[] batchrangevos = null;
	      IBatRangeMaintain batservice =
	          NCLocator.getInstance().lookup(IBatRangeMaintain.class);
	      try {
	        batchrangevos =
	            batservice.queryBatrange(batlevelids.toArray(new String[batlevelids
	                .size()]));
	      }
	      catch (BusinessException e) {
	        ExceptionUtils.wrappException(e);
	      }
	      if (batchrangevos == null || batchrangevos.length == 0) {
	        String msg =
	            nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014002_0",
	                "04014002-0021")/*@res "����ʧ�ܣ�δ�鵽�����ּ��۸���������ּ���Χ�����飡"*/;
	        ExceptionUtils.wrappBusinessException(msg);
	      }
	      else {
	        for (BatchRangeVO batchRangeVO : batchrangevos) {
	          this.batchRangeMap.put(batchRangeVO.getPrimaryKey(), batchRangeVO);
	        }
	      }
	    }
	  }

	  /**
	   * ѯ�۸����
	   * 
	   * @param pk_org ������֯
	   * @param csendtypeid ���䷽ʽ
	   * @param ccarrierid ������
	   * @return �۸����
	   */
	  private FeeTariffDefVO queryTariffDef(String pk_org, String csendtypeid,
	      String ccarrierid) {
	    FeeTariffDefVO tariffdefvo = null;
	    ITariffdefMaintain defservice =
	        NCLocator.getInstance().lookup(ITariffdefMaintain.class);
	    try {
	      tariffdefvo =
	          defservice.queryFeeTariffDefVO(pk_org, csendtypeid, ccarrierid);
	    }
	    catch (BusinessException e) {
	      ExceptionUtils.wrappException(e);
	    }
	    if (tariffdefvo == null) {
//	      String msg =
//	          nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014002_0",
//	              "04014002-0022")/*@res "����ʧ�ܣ�δƥ�䵽�۸���壬���飡"*/;
//	      ExceptionUtils.wrappBusinessException(msg);
	    }
	    return tariffdefvo;
	  }

	  /**
	   * ��ȡ�۸����ά��
	   * 
	   * @param tariffdefvo �۸����
	   * @return ����ά��
	   */
	  private List<String> getCollectFields(FeeTariffDefVO tariffdefvo) {
	    List<String> fields = new ArrayList<String>();
	    FeeTariffDefBVO[] bvos = tariffdefvo.getChildrenVO();
	    for (FeeTariffDefBVO bvo : bvos) {
	      if (!bvo.getBbshowflag().booleanValue()
	          && !bvo.getBhshowflag().booleanValue()) {
	        continue;
	      }
	      String fdataitemflag = bvo.getFdataitemflag();
	      if (!fdataitemflag.startsWith("vnote")
	          && !fdataitemflag.startsWith("vbnote")) {
	        fields.add(fdataitemflag);
	      }
	    }
	    return fields;
	  }

	  /**
	   * ���� ����id���������ڲ�ѯ�۸����ϸ����
	   * 
	   * @param vo
	   * @param tariffdefvo
	   * @return Ӧ���˷Ѽ۸��ά��
	   */

	  private FeeTariffVO[] queryTariff(FeeCalculateVO vo,
	      FeeTariffDefVO tariffdefvo) {
	    FeeTariffVO[] tariffs = null;
	    ITariffPubManage tariffservice =
	        NCLocator.getInstance().lookup(ITariffPubManage.class);
	    try {
	      String pk_tariffdef = tariffdefvo.getParentVO().getPk_tariffdef();

	      UFDate ddelivdate = vo.getddelivdate();
	      tariffs = tariffservice.queryFeeTariffVOBySql(pk_tariffdef, ddelivdate);
	    }
	    catch (BusinessException e) {
	      ExceptionUtils.wrappException(e);
	    }
	    if (tariffs == null || tariffs.length == 0) {
	    	
	      /*String msg =
	          nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014002_0",
	              "04014002-0023")@res "����ʧ�ܣ�δ��ѯ���۸����ϸ���ݣ����飡";
	      ExceptionUtils.wrappBusinessException(msg);*/
	    }
	    return tariffs;
	  }

	  /**
	   * ���۸����ϸ����ת��Ϊmap
	   * 
	   * @param fields �۸����ά��
	   * @param tariffs �۸����ϸ����
	   * @return map
	   */
	  private Map<String, FeeTariffVO> changeFeeTariffToMap(List<String> fields,
	      FeeTariffVO[] tariffs) {
	    Map<String, FeeTariffVO> tariffmap = new HashMap<String, FeeTariffVO>();
	    for (FeeTariffVO tariff : tariffs) {
	      StringBuilder sb = new StringBuilder();
	      String str = "";
	      for (String field : fields) {
	        /*���fieldΪ���������ֶ�(cmarbasclassid)�������⸳ֵ*/
	        if (field.equals("cmarbasclassid")) {
	          str = field + ":" + tariff.getParentVO().getMaterialinnercode() + ",";
	        }
	        else {
	          if (field.equals("dbegindate") || field.equals("denddate")) {
	            continue;
	          }
	          sb.append(field);
	          sb.append(":");
	          sb.append(tariff.getParentVO().getAttributeValue(field));
	          sb.append(",");
	        }
	      }
	      /*���õ����*/
	      sb.append(str);
	      if (sb.length() > 0) {
	        sb.deleteCharAt(sb.length() - 1);
	      }
	      String key = sb.toString();
	      if (tariffmap.containsKey(key)) {
	        String msg =
	            nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014002_0",
	                "04014002-0024")/*@res "����ʧ�ܣ�������ͬά�ȵļ۸��ά�����ݣ����飡"*/;
	        ExceptionUtils.wrappBusinessException(msg);
	      }
	      else {
	        tariffmap.put(key, tariff);
	      }
	    }
	    return tariffmap;
	  }

	  /**
	   * ���������
	   * 
	   * @param vo �˷Ѽ��㽻�ӵ�
	   * @param feeplcyvo ���Զ���
	   * @param fields �۸�����ά��
	   * @param tariffmap �۸����ϸmap
	   * @return ��������ϸ
	   */
	  private List<ApSettleDetailVO> calculateFeeMat(FeeCalculateVO vo,
	      FeePlcyVO feeplcyvo, List<String> fields,
	      Map<String, FeeTariffVO> tariffmap) {
	    // ���ڴ���ȡ�۸񻺴�
	    this.getMapPackFromContext();
	    // ��ʽ������
	    FormulaParseFather parse = new FormulaParse();
	    List<ApSettleDetailVO> results = new ArrayList<ApSettleDetailVO>();
	    List<FeeMatCalVO> calvos = this.classfiyBillViews(vo, fields, feeplcyvo);
	    for (int i = 0; i < calvos.size(); i++) {
	      FeeMatCalVO feematcalvo = calvos.get(i);
	      String feematcalvo_key = feematcalvo.getKey();
	      FeeTariffVO tariffVO = tariffmap.get(feematcalvo_key);
	      // ���ά���������Ϸ��࣬û�е�ǰ����û��ƥ�䵽����Ҫƥ���ϼ����Ϸ���
	      if (tariffVO == null) {
	        if (feematcalvo_key.contains("cmarbasclassid")) {
	          for (int k = 1; k < feematcalvo_key.length() / 4; k++) {
	            feematcalvo_key =
	                feematcalvo_key.substring(0, feematcalvo_key.length() - 4);
	            // if (feematcalvo_key.length() == 0) {
	            // ���key���Ѿ�������cmarbasclassid��˵���Ѿ���ѯ�����ϼ�������
	            if (!feematcalvo_key.contains("cmarbasclassid")) {
	              break;
	            }
	            tariffVO = tariffmap.get(feematcalvo_key);
	            if (tariffVO != null) {
	              feematcalvo.setKey(feematcalvo_key);
	              break;
	            }
	          }
	        }
//	        if (tariffVO == null) {
//	          String msg =
//	              nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
//	                  "4014002_0", "04014002-0025")/*@res "����ʧ�ܣ�δƥ�䵽�۸��۸��޷������˷Ѽ��㣡"*/;
//	          ExceptionUtils.wrappBusinessException(msg);
//	        }
	      }
	      results.addAll(this.calFeeItem(i, parse, feematcalvo, tariffVO));
	    }
	    // ���۸񻺴浽�ڴ���
	    this.setMapPackToContext();
	    return results;
	  }

	  private void getMapPackFromContext() {
	    BSContext context = BSContext.getInstance();
	    Object sessionobject = context.getSession(DMCalculConstant.DMPRCPRICEMAP);
	    if (sessionobject != null && sessionobject instanceof PriceMapPack) {
	      PriceMapPack mappack = (PriceMapPack) sessionobject;
	      if (mappack.getNobatchPrcPrice() != null) {
	        this.nobatchPrcPrice = mappack.getNobatchPrcPrice();
	      }
	      else {
	        this.nobatchPrcPrice = new HashMap<String, UFDouble>();
	      }
	      if (mappack.getBatchPrcPrice() != null) {
	        this.batchPrcPrice = mappack.getBatchPrcPrice();
	      }
	      else {
	        this.batchPrcPrice = new HashMap<String, UFDouble>();
	      }
	    }
	    else {
	      this.nobatchPrcPrice = new HashMap<String, UFDouble>();
	      this.batchPrcPrice = new HashMap<String, UFDouble>();
	    }
	  }

	  private void setMapPackToContext() {
	    BSContext context = BSContext.getInstance();
	    PriceMapPack mappack = new PriceMapPack();
	    mappack.setBatchPrcPrice(this.batchPrcPrice);
	    mappack.setNobatchPrcPrice(this.nobatchPrcPrice);
	    context.setSession(DMCalculConstant.DMPRCPRICEMAP, mappack);
	  }

	  /**
	   * ���ݻ���ά�ȶ�views���з��࣬����FeeMatCalVO
	   * 
	   * @param vo �˷Ѽ��㽻�ӵ�
	   * @param fields ����ά��
	   * @param feeplcyvo ���Զ���
	   * @return ͬһγ�ȵĻ�����
	   */
	  private List<FeeMatCalVO> classfiyBillViews(FeeCalculateVO vo,
	      List<String> fields, FeePlcyVO feeplcyvo) {
	    List<FeeMatCalVO> vos = new ArrayList<FeeMatCalVO>();
	    MapList<String, ICalculateBillView> maplist =
	        new MapList<String, ICalculateBillView>();
	    // ����
	    String[] marBasClassKeys = new String[] {
	      MaterialVO.PK_MARBASCLASS
	    };
	    // String[] cmaterialid = null;
	    StringBuilder sb = new StringBuilder();
	    String str = "";
	    FillUpDataRule rule = new FillUpDataRule();
	    // ����ѯ���Ϸ�����forѭ���У�����sql�������� wangjlk 2014-8-25
	    // for (ICalculateBillView view : vo.getViews()) {
	    // cmaterialid = new String[] {
	    // view.getcmaterialid()
	    // };
	    List<String> cmaterialids = new ArrayList<String>();
	    for (ICalculateBillView view : vo.getViews()) {
	      cmaterialids.add(view.getcmaterialid());
	    }
	    Map<String, MaterialVO> marMap =
	        MaterialPubService.queryMaterialBaseInfo(
	            cmaterialids.toArray(new String[cmaterialids.size()]),
	            marBasClassKeys);
	    Set<String> cmarbasclassidset = new HashSet<String>();
	    // ����id���Ӧ���ϻ�������id map��MaterialVO ��һ���ϴ���󣨶Ա�String����
	    // �ں���ѭ��ibillviewʱ����Ҫ��ʹ�ö������������ã�������������ʱ���Ի����ⲿ���ڴ�ռ��
	    Map<String, String> maridclassmap = new HashMap<String, String>();
	    for (Entry<String, MaterialVO> entry : marMap.entrySet()) {
	      String cmarbasclassid = entry.getValue().getPk_marbasclass();
	      cmarbasclassidset.add(cmarbasclassid);
	      maridclassmap.put(entry.getKey(), cmarbasclassid);
	    }
	    // ���ϻ�������id �� innercode ӳ��map
	    Map<String, String> idandinnercodemap =
	        rule.queryInnercodeByMarbasclassids(cmarbasclassidset
	            .toArray(new String[cmarbasclassidset.size()]));

	    for (ICalculateBillView view : vo.getViews()) {
	      // MaterialVO materialVO = marMap.get(view.getcmaterialid());
	      // ��ȡ���ϻ�������id
	      String cmarbasclassid = maridclassmap.get(view.getcmaterialid());
	      for (String field : fields) {
	        /*���fieldΪ���������ֶ�(cmarbasclassid)�������⸳ֵ*/
	        if (field.equals("cmarbasclassid")) {
	          str = field + ":" + idandinnercodemap.get(cmarbasclassid) + ",";
	        }
	        else {
	          if (field.equals("dbegindate") || field.equals("denddate")) {
	            continue;
	          }
	          sb.append(field);
	          sb.append(":");
	          sb.append(this.getvaluebykey(ICalculateBillView.class.getName(),
	              field, view));
	          sb.append(",");
	        }
	      }
	      // ��innercodeƴ����󣬷�������ϼ�
	      sb.append(str);
	      if (sb.length() > 0) {
	        sb.deleteCharAt(sb.length() - 1);
	      }
	      maplist.put(sb.toString(), view);
	      sb.delete(0, sb.length());
	    }
	    // map��key�� ����ά��+ά��ֵ�� ��۸����ϸmap��key��ͬ
	    // �ڼ���ʱ����ֱ����key��ȡ����Ӧ�ļ۸����ϸ
	    for (Entry<String, List<ICalculateBillView>> entry : maplist.entrySet()) {
	      String key = entry.getKey();
	      List<ICalculateBillView> views = entry.getValue();
	      FeeMatCalVO calvo =
	          new FeeMatCalVO(key, views.toArray(new ICalculateBillView[views
	              .size()]), feeplcyvo);
	      vos.add(calvo);
	    }
	    return vos;
	  }

	  private Object getvaluebykey(String classname, String key, Object obj) {
	    Method m;
	    Object value = null;
	    try {
	      if (classname.equals(ICalculateBillView.class.getName())) {
	        m = ICalculateBillView.class.getMethod("get" + key);
	      }
	      else {
	        m = FeeMatCalVO.class.getMethod("get" + key);
	      }

	      value = m.invoke(obj, new Object[] {});
	    }
	    catch (SecurityException e) {
	      ExceptionUtils.wrappException(e);
	    }
	    catch (NoSuchMethodException e) {
	      String message =
	          nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
	              "04014001-0379", null, new String[] {
	                key
	              })/*@res "�˷Ѽ���ʧ�ܣ������ʽ���ڷǷ��ֶ�{0}*/;
	      ExceptionUtils.wrappBusinessException(message);
	    }
	    catch (IllegalArgumentException e) {
	      ExceptionUtils.wrappException(e);
	    }
	    catch (IllegalAccessException e) {
	      // ���淶�׳��쳣(EJB�߽�����marsh����)
	      ExceptionUtils.wrappException(e);
	    }
	    catch (InvocationTargetException e) {
	      // ���淶�׳��쳣(EJB�߽�����marsh����)
	      ExceptionUtils.wrappException(e);
	    }
	    catch (Exception e) {
	      ExceptionUtils.wrappException(e);
	    }
	    return value;
	  }

	  /**
	   * ��̯�������Ӧ�ֶ�ӳ��
	   * ��ʱ���������Ժ�ö�ٸ�Ϊstring
	   * 
	   */
	  private void createAllotflagMap() {
	    this.allotflagmap.put(FallotsetFlag.NNUM.getIntegerValue(), "nnum");
	    this.allotflagmap.put(FallotsetFlag.NASTNUM.getIntegerValue(), "nastnum");
	    this.allotflagmap.put(FallotsetFlag.NVOLUME.getIntegerValue(), "nvolumn");
	    this.allotflagmap.put(FallotsetFlag.NWEIGHT.getIntegerValue(), "nweight");
	    this.allotflagmap.put(FallotsetFlag.NSIGNNUM.getIntegerValue(), "nsignnum");
	    this.allotflagmap.put(FallotsetFlag.NSIGNASTNUM.getIntegerValue(),
	        "nsignastnum");
	    this.allotflagmap.put(FallotsetFlag.NSIGNVOLUME.getIntegerValue(),
	        "nsignvolume");
	    this.allotflagmap.put(FallotsetFlag.NSIGNWEIGHT.getIntegerValue(),
	        "nsignweight");
	    this.allotflagmap.put(FallotsetFlag.NVALUE.getIntegerValue(), "nmoney");
	  }

	  /**
	   * ����������̯����ϸ��
	   * 
	   * @param n �������
	   * @param parse ��ʽ������
	   * @param calvo ͬһγ�ȵĻ�����
	   * @param tariffvo �۸��ά������
	   * @return ��������ϸ
	   */
	  private List<ApSettleDetailVO> calFeeItem(int n, FormulaParseFather parse,
	      FeeMatCalVO calvo, FeeTariffVO tariffvo) {
	    List<ApSettleDetailVO> results = new ArrayList<ApSettleDetailVO>();
	    FeePlcyVO feeplcyvo = calvo.getFeeplcyvo();
	    for (FeePlcyFeeVO feevo : feeplcyvo.getFeePlcyFeeVO()) {
	      String vfeeformcode = feevo.getVfeeformcode();
	      parse.setExpress(vfeeformcode);
	      VarryVO varryVO = parse.getVarry();
	      String[] itemcodes = varryVO.getVarry();
	      MapList<String, UFDouble> maplist =
	          this.calItemValue(parse, calvo, tariffvo, itemcodes);
	      // �����۸����������ݹ�ʽʱ�����ù�ʽ���������У�����������������һ�¡�
	      parse.setExpress(vfeeformcode);
	      parse.setDataSArray(maplist.toMap());
	      UFDouble value = UFDouble.ZERO_DBL;
	      if (parse.getValueAsObject() instanceof Integer) {
	        value = new UFDouble(((Integer) parse.getValueAsObject()).intValue());
	      }
	      else if (parse.getValueAsObject() instanceof UFDouble) {
	        value = (UFDouble) parse.getValueAsObject();
	      }
	      else {
	        String msg =
	            NCLangResOnserver.getInstance().getStrByID("4014002_0",
	                "04014002-0043", null, new String[] {
	                  feevo.getVfeeformname()
	                })/*����ʧ�ܣ������ʽ:{0}�޷��������������飡*/;
	        ExceptionUtils.wrappBusinessException(msg);
	      }
	      value = ScaleUtils.getScaleUtilAtBS().adjustMnyScale(value, this.curr);

	      AllotFeePackVO packvo = new AllotFeePackVO();
	      packvo.setAllotflag(feevo.getFallotsetflag());
	      packvo.setFeeitemid(feevo.getCfeeitemid());
	      packvo.setNfeemny(value);
	      // ������� �����������������˷���ϸ ��Ҫ�־û������ݡ����������� ���㵥�޸ķ�����������ʱ���з�̯��Ҫ��
	      packvo.setNum(n);
	      packvo.setViews(calvo.getViews());
	      results.addAll(this.allotFeeToDetails(packvo));
	    }
	    return results;
	  }

	  /**
	   * ��������ʽ��������Ӧֵ
	   * 
	   * @param parse ��ʽ������
	   * @param novdefvo ����VO
	   * @param tariffvo �۸����ϸ
	   * @param itemcodes ��������
	   * @return key:��ʽ������value:��ʽ����ֵ
	   */
	  private MapList<String, UFDouble> calItemValue(FormulaParseFather parse,
	      FeeMatCalVO novdefvo, FeeTariffVO tariffvo, String[] itemcodes) {
	    MapList<String, UFDouble> maplist = new MapList<String, UFDouble>();
	    IFormulaService service =
	        NCLocator.getInstance().lookup(IFormulaService.class);
	    FormularVO[] formularVOs = null;
	    try {
	      formularVOs = service.queryAllDMFormular();
	    }
	    catch (BusinessException e) {
	      ExceptionUtils.wrappException(e);
	    }
	    for (String itemcode : itemcodes) {
	      UFDouble price = null;
	      // ��������ʽ�����۸����Ҫȡ���۸����Ӧ�۸�
	      if (itemcode.startsWith("nprice")) {
	        FeePlcyPrcVO prcvo = this.prcvomap.get(itemcode);
	        if (prcvo == null) {
	          String msg =
	              nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
	                  "4014002_0", "04014002-0026")/*@res "����ʧ�ܣ�δƥ�䵽�����õļ۸�����飡"*/;
	          ExceptionUtils.wrappBusinessException(msg);
	        }
	        // �۸�������������ּ�����Ҫ��ͨ�������ּ����ݹ�ʽ�������������ֵ
	        // ƥ���Ӧ�������ּ���Χȡ��
	        else if (prcvo.getBbatprcflag().booleanValue()) {
	          String vbatformulacode = prcvo.getVbatformulacode();
	          UFDouble batchvalue =
	              this.getPrcBatchValue(parse, novdefvo, vbatformulacode,
	                  formularVOs);
	          String cbatlevelid = prcvo.getCbatlevelid();
	          String cbatlevelbid =
	              this.getbatlevel(prcvo, batchvalue, cbatlevelid);
	          price = this.getBatchPrcPrice(cbatlevelbid, tariffvo);
	        }
	        else {
	          price = this.getNoBatchPrcPrice(itemcode, tariffvo);
	        }
	        if (price == null) {
	          String msg =
	              NCLangResOnserver.getInstance().getStrByID("4014002_0",
	                  "04014002-0044", null, new String[] {
	                    itemcode.replace("nprice", "")
	                  })/*����ʧ�ܣ��۸���{0}�Ķ�Ӧ�۸�Ϊ��!*/;
	          ExceptionUtils.wrappBusinessException(msg);
	        }
	      }
	      else {
	        price =
	            (UFDouble) this.getvaluebykey(FeeMatCalVO.class.getName(),
	                itemcode, novdefvo);
	        if (price == null && formularVOs != null) {
	          for (FormularVO formularVO : formularVOs) {
	            if (formularVO.getVcode().equals(itemcode)) {
	              String msg =
	                  NCLangResOnserver.getInstance().getStrByID("4014002_0",
	                      "04014002-0045", null, new String[] {
	                        formularVO.getVname()
	                      })/*����ʧ�ܣ�{0}Ϊ��!*/;
	              ExceptionUtils.wrappBusinessException(msg);
	            }
	          }
	        }
	      }

	      maplist.put(itemcode, price);
	    }
	    return maplist;
	  }

	  /**
	   * ����۸����������ݹ�ʽֵ
	   * 
	   * @param parse ��ʽ������
	   * @param novdefvo ����VO
	   * @param vbatformulacode �۸����������ݹ�ʽ
	   * @param formularVOs ������ʽ
	   * @return �������ݹ�ʽֵ
	   */
	  private UFDouble getPrcBatchValue(FormulaParseFather parse,
	      FeeMatCalVO novdefvo, String vbatformulacode, FormularVO[] formularVOs) {
	    parse.setExpress(vbatformulacode);
	    VarryVO varry = parse.getVarry();
	    String[] prcitemcodes = varry.getVarry();
	    MapList<String, UFDouble> valuelist = new MapList<String, UFDouble>();
	    for (String code : prcitemcodes) {
	      UFDouble codevalue =
	          (UFDouble) this.getvaluebykey(FeeMatCalVO.class.getName(), code,
	              novdefvo);
	      if (codevalue == null) {
	        for (FormularVO formularVO : formularVOs) {
	          if (formularVO.getVcode().equals(code)) {
	            String msg =
	                NCLangResOnserver.getInstance().getStrByID("4014002_0",
	                    "04014002-0045", null, new String[] {
	                      formularVO.getVname()
	                    })/*����ʧ�ܣ�{0}Ϊ��!*/;
	            ExceptionUtils.wrappBusinessException(msg);
	          }
	        }
	      }
	      valuelist.put(code, codevalue);
	    }
	    parse.setDataSArray(valuelist.toMap());
	    Object result = parse.getValueAsObject();
	    UFDouble batchvalue = null;
	    // ���صĽ�� �п���ΪInteger������������������ΪUFDouble
	    if (result instanceof Integer) {
	      batchvalue = new UFDouble(((Integer) result).intValue());
	    }
	    else if (result instanceof UFDouble) {
	      batchvalue = (UFDouble) result;
	    }
	    else {
	      String msg =
	          nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014002_0",
	              "04014002-0027")/*@res "����ʧ�ܣ������ּ���ʽ����������飡"*/;
	      ExceptionUtils.wrappBusinessException(msg);
	    }
	    return batchvalue;
	  }

	  /**
	   * ������������ֵ��ȡ���������ּ��ӱ�ID
	   * 
	   * @param prcvo �۸���vo
	   * @param batchvalue ��������ֵ
	   * @param cbatlevelid �����ּ�����ID
	   * @return �����ּ��ӱ�ID
	   */
	  private String getbatlevel(FeePlcyPrcVO prcvo, UFDouble batchvalue,
	      String cbatlevelid) {
	    BatchRangeVO batchrangevo = this.batchRangeMap.get(cbatlevelid);
	    String cbatlevelbid = null;
	    for (BatchRangeBVO bvo : batchrangevo.getChildrenVO()) {
	      UFDouble nrangefrom = bvo.getNrangefrom();
	      UFDouble nrangeto = bvo.getNrangeto();
	      if (batchvalue.compareTo(nrangefrom) >= 0
	          && (nrangeto == null || batchvalue.compareTo(nrangeto) < 0)) {
	        cbatlevelbid = bvo.getPk_batrange_b();
	        break;
	      }
	    }
	    if (cbatlevelbid == null) {
	      String msg =
	          NCLangResOnserver.getInstance().getStrByID("4014002_0",
	              "04014002-0046", null, new String[] {
	                prcvo.getVshowname()
	              })/*����ʧ�ܣ�ƥ��{0}�����ּ���Χ����*/;
	      ExceptionUtils.wrappBusinessException(msg);
	    }
	    return cbatlevelbid;
	  }

	  /**
	   * ��ȡ�������ּ��ļ۸�����
	   * 
	   * @param itemcode �۸������
	   * @param tariffvo �۸��ά��
	   * @return �۸�����
	   */
	  private UFDouble getNoBatchPrcPrice(String itemcode, FeeTariffVO tariffvo) {
	    UFDouble price =
	        this.nobatchPrcPrice.get(tariffvo.getPrimaryKey() + itemcode);
	    if (price == null) {
	      price = (UFDouble) tariffvo.getParentVO().getAttributeValue(itemcode);
	      this.nobatchPrcPrice.put(tariffvo.getPrimaryKey() + itemcode, price);
	    }
	    return price;
	  }

	  /**
	   * ��ȡ�����ּ��ļ۸�����
	   * 
	   * @param cbatlevelbid �����ּ�ID
	   * @param tariffvo �۸��ά��
	   * @return �۸�����
	   */
	  private UFDouble getBatchPrcPrice(String cbatlevelbid, FeeTariffVO tariffvo) {
	    UFDouble price =
	        this.batchPrcPrice.get(tariffvo.getPrimaryKey() + cbatlevelbid);
	    if (price == null) {
	      for (FeeTariffBatVO batvo : tariffvo.getChildrenVO()) {
	        if (batvo.getCbatlevelbid().equals(cbatlevelbid)) {
	          price = batvo.getNprice();
	          break;
	        }
	      }
	      this.batchPrcPrice.put(tariffvo.getPrimaryKey() + cbatlevelbid, price);
	    }
	    return price;
	  }

	  /**
	   * �����������̯�����˷���ϸ
	   * 
	   * @param packvo �˷ѷ�̯������
	   * @return �˷���ϸ
	   */
	  private List<ApSettleDetailVO> allotFeeToDetails(AllotFeePackVO packvo) {
	    String field = this.chooseAllotFlag(packvo);
	    ICalculateBillView[] views = packvo.getViews();
	    // �ܷ�̯����ֵ
	    UFDouble ncalculmny = UFDouble.ZERO_DBL;
	    Map<String, UFDouble> culmnymap = new HashMap<String, UFDouble>();
	    for (ICalculateBillView view : views) {
	      UFDouble culmny =
	          (UFDouble) this.getvaluebykey(ICalculateBillView.class.getName(),
	              field, view);
	      if (culmny == null) {
	        Integer allotflag = packvo.getAllotflag();
	        FallotsetFlag fallotsetFlag =
	            MDEnum.valueOf(FallotsetFlag.class, allotflag);
	        String msg =
	            NCLangResOnserver.getInstance().getStrByID("4014002_0",
	                "04014002-0047", null, new String[] {
	                  fallotsetFlag.getName()
	                })/*����ʧ�ܣ���̯���ݣ�{0}Ϊ��*/;
	        ExceptionUtils.wrappBusinessException(msg);
	      }
	      culmnymap.put(view.getcbill_bid(), culmny);
	      ncalculmny = MathTool.add(ncalculmny, culmny);
	    }
	    if (ncalculmny.equals(UFDouble.ZERO_DBL)) {
	      FallotsetFlag allot =
	          MDEnum.valueOf(FallotsetFlag.class, packvo.getAllotflag());
	      String msg =
	          NCLangResOnserver.getInstance().getStrByID("4014002_0",
	              "04014002-0048", null, new String[] {
	                allot.getName()
	              })/*����ʧ�ܣ���̯���ݣ�{0}��ֵΪ0�����飡*/;
	      ExceptionUtils.wrappBusinessException(msg);
	    }
	    Map<String, UFDouble> feemap = this.allotfee(packvo, ncalculmny, culmnymap);
	    List<ApSettleDetailVO> results =
	        this.createDetailVOs(packvo, culmnymap, feemap);

	    return results;
	  }

	  /**
	   * ��ȡ��̯�����ֶ�
	   * 
	   * @param packvo �˷ѷ�̯������
	   * @return ��̯�����ֶ�
	   */
	  private String chooseAllotFlag(AllotFeePackVO packvo) {
	    Integer allotflag = packvo.getAllotflag();
	    if (FallotsetFlag.MAXWV.equalsValue(allotflag)) {
	      UFDouble volumn = packvo.getnvolumn();
	      UFDouble weight = packvo.getnweight();
	      UFDouble nquotiety = packvo.getViews()[0].getnexchangerate();
	      if (weight == null) {
	        String msg =
	            nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014002_0",
	                "04014002-0028")/*@res "����ʧ�ܣ����Ϊ�գ��޷�����MAX(���������*�����)"*/;
	        ExceptionUtils.wrappBusinessException(msg);
	      }
	      else if (volumn == null) {
	        String msg =
	            nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014002_0",
	                "04014002-0028")/*@res "����ʧ�ܣ����Ϊ�գ��޷�����MAX(���������*�����)"*/;
	        ExceptionUtils.wrappBusinessException(msg);
	      }
	      else if (nquotiety == null) {
	        String msg =
	            nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014002_0",
	                "04014002-0029")/*@res "δѯ���������ϵ����"*/;
	        ExceptionUtils.wrappBusinessException(msg);
	      }
	      else if (weight.compareTo(volumn.multiply(nquotiety)) > 0) {
	        return DelivBillBVO.NWEIGHT;
	      }
	      return DelivBillBVO.NVOLUMN;
	    }
	    return this.allotflagmap.get(allotflag);
	  }

	  /**
	   * �˷ѷ�̯
	   * 
	   * @param packvo �˷ѷ�̯������
	   * @param ncalculmny �ܷ�̯����ֵ
	   * @param culmnymap ��̯����ֵmap
	   * @return �˷���ϸmap
	   */
	  private Map<String, UFDouble> allotfee(AllotFeePackVO packvo,
	      UFDouble ncalculmny, Map<String, UFDouble> culmnymap) {
	    ICalculateBillView[] views = packvo.getViews();
	    UFDouble nfeemny = packvo.getNfeemny();
	    // �ѷ�̯��� ������
	    UFDouble ntotalMny = UFDouble.ZERO_DBL;
	    UFDouble nnewMny;
	    Map<String, UFDouble> feemap = new HashMap<String, UFDouble>();
	    for (ICalculateBillView view : views) {
	      // ��̯����ֵ
	      UFDouble culmny = culmnymap.get(view.getcbill_bid());
	      nnewMny = nfeemny.multiply(culmny).div(ncalculmny);
	      nnewMny =
	          ScaleUtils.getScaleUtilAtBS().adjustMnyScale(nnewMny, this.curr);
	      ntotalMny = ntotalMny.add(nnewMny);
	      feemap.put(view.getcbill_bid(), nnewMny);
	    }
	    // ����Ѿ���̯�ĺϼƽ����ڲ��������
	    if (ntotalMny.compareTo(nfeemny) != 0) {
	      nfeemny = nfeemny.sub(ntotalMny);
	      // �����һ����ϸ��ʼ���ң����������һ����̯����ֵ������0����ϸ��
	      for (int i = views.length - 1; i >= 0; i--) {
	        ICalculateBillView view = views[i];
	        if (culmnymap.get(view.getcbill_bid()).compareTo(UFDouble.ZERO_DBL) > 0) {
	          UFDouble feemny = feemap.get(view.getcbill_bid());
	          feemny = MathTool.add(feemny, nfeemny);
	          feemap.put(view.getcbill_bid(), feemny);
	          break;
	        }
	      }
	    }
	    return feemap;
	  }

	  /**
	   * ���ݷ�̯�˷Ѵ����˷���ϸ
	   * 
	   * @param packvo �˷ѷ�̯������
	   * @param culmnymap ��̯����ֵmap
	   * @param feemap �˷���ϸmap
	   * @return results
	   */
	  private List<ApSettleDetailVO> createDetailVOs(AllotFeePackVO packvo,
	      Map<String, UFDouble> culmnymap, Map<String, UFDouble> feemap) {
	    List<ApSettleDetailVO> results = new ArrayList<ApSettleDetailVO>();
	    ICalculateBillView[] views = packvo.getViews();
	    for (ICalculateBillView view : views) {
	      // ��̯����ֵ
	      UFDouble culmny = culmnymap.get(view.getcbill_bid());
	      // �˷ѽ��
	      UFDouble feemny = feemap.get(view.getcbill_bid());
	      ApSettleDetailVO vo = new ApSettleDetailVO();
	      vo.setVbillcode(view.getvdelivbillcode());
	      vo.setCfeeitemid(packvo.getFeeitemid());
	      vo.setNfeemny(feemny);
	      vo.setFallotflag(packvo.getAllotflag());
	      // ��¼��̯����ֵ���� ���㵥�޸ķ�����������ʱ������Ҫ��ȡ���Զ�����ȡȥ��̯����Ȼ������̯����ֵ
	      vo.setVallotvalue(culmny.toString());
	      vo.setIgroupingno(Integer.valueOf(packvo.getNum()));
	      vo.setCbillareaid(view.getcbillhid());
	      // �洢�������Ϊ�� ���㵥�޸ķ������� �����ڸ�����̯ʱʹ�ã� ���ʼ�����䵥������Ľ�����׼
	      // ��Ϊ���ֱ���ý�������Ϊ�޸ĺ� �ִ����β����Ա����������ͬ��
	      vo.setNcalculatemny(feemny);
	      for (String key : DMCalculConstant.SAMEFIELDS) {
	        Object value =
	            this.getvaluebykey(ICalculateBillView.class.getName(), key, view);
	        vo.setAttributeValue(key, value);
	      }
	      results.add(vo);
	    }
	    return results;
	  }
	}
