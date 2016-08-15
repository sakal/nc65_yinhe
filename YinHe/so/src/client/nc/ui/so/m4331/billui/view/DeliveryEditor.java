package nc.ui.so.m4331.billui.view;

import java.util.Map;

import nc.itf.scmpub.reference.uap.group.SysInitGroupQuery;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillItem;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyRowChangedEvent;
import nc.ui.pubapp.uif2app.model.IAppModelEx;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.scmf.ic.batchcode.ref.ScmBatchAdaptor;
import nc.ui.scmf.ic.onhand.OnhandPanelAdaptor;
import nc.ui.scmf.ic.onhand.OnhandPanelSrc;
import nc.ui.scmpub.util.BillCardPanelUtils;
import nc.ui.so.m4331.billui.model.DeliveryManageModel;
import nc.ui.so.pub.keyvalue.CardKeyValue;
import nc.ui.so.pub.listener.SOBillTotalListener;
import nc.vo.scmf.ic.onhand.OnhandDimParamVO;
import nc.vo.so.m4331.entity.DeliveryBVO;
import nc.vo.so.m4331.entity.DeliveryHVO;
import nc.vo.so.pub.SOConstant;
import nc.vo.so.pub.keyvalue.IKeyValue;

/**
 * ��������Ƭ����
 * 
 * @since 6.3
 * @version 2014-01-13 15:04:02
 * @author ����
 */
public class DeliveryEditor extends ShowUpableBillForm implements
		OnhandPanelSrc {
  
  /**
   * // modify by jilu for EHP1���̵�633 20140703
   * ��ȡ���������
   * 
   * @return Map<String, String>
   */
  public Map<String, String> getCachematerialid() {
    return this.cachematerialid;
  }

  /**
   * ���û��������
   * 
   * @param cachematerialid
   */
  public void setCachematerialid(Map<String, String> cachematerialid) {
    this.cachematerialid = cachematerialid;
  }

  private Map<String, String> cachematerialid;

	// ����λ ������, ���۵�λ�����ۻ����� ˰�ʡ���Ʒ�ۿ� ��Ʒ ����˰���ۡ�����˰����
	// ����˰���ۡ�����˰���� ��˰���ۡ���˰���� ��˰���ۡ���˰���� ˰���˰���
	// ��˰�ϼơ��ۿ۶� �۱�����,ԭ���� ��������������� ���ϱ��롢���ϰ汾 ����������֯
	private static String[] bodyKey = new String[] {
			// �������OID
			DeliveryBVO.BADVFEEFLAG, DeliveryBVO.CSETTLEORGID,
			DeliveryBVO.CARORGID, DeliveryBVO.CPROFITCENTERID,
			DeliveryBVO.CSENDSTOCKORGID,DeliveryBVO.CSENDSTOCKORGVID, DeliveryBVO.CUNITID,
			DeliveryBVO.VCHANGERATE, DeliveryBVO.CQTUNITID,
			DeliveryBVO.VQTUNITRATE, DeliveryBVO.NTAXRATE,
			DeliveryBVO.NITEMDISCOUNTRATE, DeliveryBVO.BLARGESSFLAG,
			DeliveryBVO.NORIGTAXPRICE, DeliveryBVO.NORIGPRICE,
			DeliveryBVO.NORIGTAXNETPRICE, DeliveryBVO.NORIGNETPRICE,
			DeliveryBVO.NQTORIGTAXPRICE, DeliveryBVO.NQTORIGPRICE,
			DeliveryBVO.NQTORIGTAXNETPRC, DeliveryBVO.NQTORIGNETPRICE,
			DeliveryBVO.NORIGMNY, DeliveryBVO.NORIGTAXMNY,
			DeliveryBVO.NORIGDISCOUNT, DeliveryBVO.NEXCHANGERATE,
			DeliveryBVO.CCURRENCYID, DeliveryBVO.CORIGCURRENCYID,
//			DeliveryBVO.CMATERIALID, DeliveryBVO.CMATERIALVID,
			DeliveryBVO.CSALEORGID, DeliveryBVO.NTAX, DeliveryBVO.NTAXMNY,
			DeliveryBVO.NMNY, DeliveryBVO.NTAXNETPRICE, DeliveryBVO.NTAXPRICE,
			DeliveryBVO.NNETPRICE, DeliveryBVO.NPRICE,
			DeliveryBVO.CSETTLEORGID, DeliveryBVO.CSETTLEORGVID,
			DeliveryBVO.NTOTALARNUM, DeliveryBVO.NTOTALELIGNUM,
			DeliveryBVO.NTOTALESTARNUM, DeliveryBVO.NTOTALNOTOUTNUM,
			DeliveryBVO.NTOTALOUTNUM, DeliveryBVO.NTOTALREPORTNUM,
			DeliveryBVO.NTOTALRUSHNUM, DeliveryBVO.NTOTALTRANSNUM,
			DeliveryBVO.NTOTALUNELIGNUM, DeliveryBVO.BOUTENDFLAG,
			DeliveryBVO.BTRANSENDFLAG, DeliveryBVO.CARORGID,
			DeliveryBVO.CARORGVID, DeliveryBVO.CSETTLEORGID,
			DeliveryBVO.CSETTLEORGVID, DeliveryBVO.CSALEORGID,
			DeliveryBVO.CSALEORGVID, DeliveryBVO.NQTPRICE,
			DeliveryBVO.NQTNETPRICE, DeliveryBVO.NQTTAXNETPRICE,
			DeliveryBVO.NQTTAXPRICE, DeliveryBVO.NDISCOUNT,
			
			DeliveryBVO.CFIRSTBID,
			DeliveryBVO.CFIRSTID,
			DeliveryBVO.VFIRSTCODE,
			DeliveryBVO.VFIRSTROWNO,
			DeliveryBVO.VFIRSTTRANTYPE,
			DeliveryBVO.VFIRSTTYPE,
			// ��Դ������Ϣ
			DeliveryBVO.CSRCBID,
			DeliveryBVO.CSRCID,
			DeliveryBVO.VSRCCODE,
			DeliveryBVO.VSRCROWNO,
			DeliveryBVO.VSRCTRANTYPE,
			DeliveryBVO.VSRCTYPE,
			DeliveryBVO.BCLOSESRCFLAG,
			};

	/**
	 * �������������������ֶεı༭��
	 * 
	 * @author ף����
	 * @time 2010-6-8 ����11:12:06
	 */
	private static String[] headKey = new String[] { DeliveryHVO.CSENDDEPTID,
			DeliveryHVO.CBIZTYPEID, DeliveryHVO.NTOTALASTNUM,
			DeliveryHVO.NTOTALPIECE, DeliveryHVO.NTOTALVOLUME,
			DeliveryHVO.NTOTALWEIGHT, DeliveryHVO.FSTATUSFLAG,
			DeliveryHVO.DMAKEDATE, DeliveryHVO.APPROVER,
			DeliveryHVO.TAUDITTIME, DeliveryHVO.CREATOR, DeliveryHVO.MODIFIER,
			DeliveryHVO.MODIFIEDTIME, DeliveryHVO.CREATIONTIME,
			DeliveryHVO.BILLMAKER };

	private static final long serialVersionUID = -4898097866857994181L;

	@Override
	public void addCardBodyRowChangedEvent(
			IAppEventHandler<CardBodyRowChangedEvent> l) {
		((IAppModelEx) this.getModel()).addAppEventListener(
				CardBodyRowChangedEvent.class, l);
	}

	/**
	 * �û��ִ������
	 */
	private OnhandPanelAdaptor adaptor;

	public OnhandPanelAdaptor getExtendedPanel() {
		return this.adaptor;
	}

	public void setExtendedPanel(OnhandPanelAdaptor adaptor) {
		this.adaptor = adaptor;
	}

	@Override
	public OnhandDimParamVO getQryOnhandDim(int row) {
		IKeyValue util = new CardKeyValue(this.getBillCardPanel());
		OnhandDimParamVO paraVO = new OnhandDimParamVO();
		paraVO.setPk_group(this.getModel().getContext().getPk_group());
		paraVO.setCastunitid(util.getBodyStringValue(row,
				DeliveryBVO.CASTUNITID));
		paraVO.setClocationid(util
				.getBodyStringValue(row, DeliveryBVO.CSPACEID));

		paraVO.setCmaterialoid(util.getBodyStringValue(row,
				DeliveryBVO.CMATERIALID));
		paraVO.setCmaterialvid(util.getBodyStringValue(row,
				DeliveryBVO.CMATERIALVID));
		paraVO.setCproductorid(util.getBodyStringValue(row,
				DeliveryBVO.CPRODUCTORID));
		paraVO.setCprojectid(util.getBodyStringValue(row,
				DeliveryBVO.CPROJECTID));
		paraVO.setCvendorid(util.getBodyStringValue(row, DeliveryBVO.CVENDORID));
		paraVO.setCwarehouseid(util.getBodyStringValue(row,
				DeliveryBVO.CSENDSTORDOCID));
		paraVO.setPk_batchcode(util.getBodyStringValue(row,
				DeliveryBVO.PK_BATCHCODE));
		paraVO.setVbatchcode(util.getBodyStringValue(row,
				DeliveryBVO.VBATCHCODE));
		paraVO.setPk_org(util.getBodyStringValue(row,
				DeliveryBVO.CSENDSTOCKORGID));
		paraVO.setPk_org_v(util.getBodyStringValue(row,
				DeliveryBVO.CSENDSTOCKORGVID));
		paraVO.setVbatchcode(util.getBodyStringValue(row,
				DeliveryBVO.VBATCHCODE));
		paraVO.setVchangerate(util.getBodyStringValue(row,
				DeliveryBVO.VCHANGERATE));
		paraVO.setVfree1(util.getBodyStringValue(row, DeliveryBVO.VFREE1));
		paraVO.setVfree2(util.getBodyStringValue(row, DeliveryBVO.VFREE2));
		paraVO.setVfree3(util.getBodyStringValue(row, DeliveryBVO.VFREE3));
		paraVO.setVfree4(util.getBodyStringValue(row, DeliveryBVO.VFREE4));
		paraVO.setVfree5(util.getBodyStringValue(row, DeliveryBVO.VFREE5));
		paraVO.setVfree6(util.getBodyStringValue(row, DeliveryBVO.VFREE6));
		paraVO.setVfree7(util.getBodyStringValue(row, DeliveryBVO.VFREE7));
		paraVO.setVfree8(util.getBodyStringValue(row, DeliveryBVO.VFREE8));
		paraVO.setVfree9(util.getBodyStringValue(row, DeliveryBVO.VFREE9));
		paraVO.setVfree10(util.getBodyStringValue(row, DeliveryBVO.VFREE10));
		return paraVO;
	}

	/**
	 * ���෽����д
	 * 
	 * @see nc.ui.pubapp.uif2app.view.ShowUpableBillForm#initUI()
	 */
	@Override
	public void initUI() {
		super.initUI();
    // ��Ƭ����������úϼ���
    BillCardPanel cardPanel = this.getBillCardPanel();
    cardPanel.getBodyPanel().setTotalRowShow(true);
    IKeyValue keyValue = new CardKeyValue(cardPanel);
    SOBillTotalListener totallis = new SOBillTotalListener(keyValue);
    cardPanel.getBillModel().addTotalListener(totallis);
    
    cardPanel.getBodyPanel().setTotalRowShow(true);
		this.getBillOrgPanel().setLabelName(
				NCLangRes.getInstance()
						.getStrByID("4006002_0", "04006002-0114")
		/* ������֯ */);
		// ��ʼ���༭��
		this.initEditEnable();
		this.initRefCondition();
		this.setItemName();
		this.initFillEnabled(this.getBillCardPanel());
	}

	private void initEditEnable() {
		for (String key : DeliveryEditor.headKey) {
			BillItem headItem = this.getBillCardPanel().getHeadTailItem(key);
			if (null != headItem) {
				headItem.setEdit(false);
			}
		}
		for (String key : DeliveryEditor.bodyKey) {
			BillItem bodyitem = this.getBillCardPanel().getBodyItem(key);
			if (null != bodyitem) {
				//bodyitem.setEdit(false);
				//���ٿ��Ʊ����еĿɱ༭�ԣ�ͨ��ǰ��ģ�����
				bodyitem.setEdit(true);
			}
		}

	}

	/**
	 * ��ʼ������ı༭��
	 * 
	 * @param cardPanel
	 */
	private void initFillEnabled(BillCardPanel cardPanel) {
		BillCardPanelUtils util = new BillCardPanelUtils(cardPanel);
		util.disableItemsFill();
		util.enableItemsFill(SOConstant.DELIVERYFILLENABLEDKEY);

		// �Զ�����������༭
		for (int i = 1; i < 21; i++) {
			BillItem bodyitem = this.getBillCardPanel().getBodyItem(
					SOConstant.VBDEF + i);
			bodyitem.setFillEnabled(true);
		}

	}

	private void initRefCondition() {
		if (SysInitGroupQuery.isICEnabled()) {
			ScmBatchAdaptor scmbach = new ScmBatchAdaptor(
					"nc.ui.ic.batchcode.ref.BatchRefPane");
			UIRefPane uiref = scmbach.getRefPane();
			// �������β���
			// BatchRefPane batchref = new BatchRefPane();
			this.getBillCardPanel().getBodyItem(DeliveryBVO.VBATCHCODE)
					.setComponent(uiref);
		}
	}

	private void setItemName() {
		DeliveryManageModel manageModel = (DeliveryManageModel) this.getModel();
		Map<String, String> map = manageModel.getWeightAndVolName();
		String name = map.get(SOConstant.BD305);
		if (null != name && !"".equals(name)) {
			BillItem weightItem = this.getBillCardPanel().getHeadItem(
					DeliveryHVO.NTOTALWEIGHT);
			weightItem.setName(NCLangRes.getInstance().getStrByID("4006002_0",
					"04006002-0115", null, new String[] { name })/* ������({0}) */);
		}
		name = map.get(SOConstant.BD304);
		if (null != name && !"".equals(name)) {
			BillItem volItem = this.getBillCardPanel().getHeadItem(
					DeliveryHVO.NTOTALVOLUME);
			volItem.setName(NCLangRes.getInstance().getStrByID("4006002_0",
					"04006002-0116", null, new String[] { name })/* �����({0}) */);
		}
	}
}
