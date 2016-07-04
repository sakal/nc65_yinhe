package nc.impl.dm.m4804.rule;

import java.util.HashMap;
import java.util.Map;

import nc.vo.dm.m4804.entity.DelivBillAggVO;
import nc.vo.dm.m4804.entity.DelivBillBVO;
import nc.vo.dm.m4804.entity.DelivBillHVO;
import nc.vo.dm.m4804trantype.entity.M4804TranTypeVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.pub.PubAppTool;

import nc.bs.ml.NCLangResOnserver;

import nc.impl.pubapp.pattern.rule.IRule;

/**
 * ���䵥��������У��</br>
 * ���䵥�Ľ����������Ƿ�֧�����Ƶı�־�����Ƶĵ��Ӳ���ʹ�ò�֧�����ƵĽ������͡�
 * 
 * @since 6.0
 * @version 2011-4-20 ����04:01:01
 * @author ����
 */
public class TrantypeValidation implements IRule<DelivBillAggVO> {

  private Map<String, M4804TranTypeVO> tranTypeMap =
      new HashMap<String, M4804TranTypeVO>();

  /**
   * ������
   * 
   * @param tranTypeMap
   */
  public TrantypeValidation(Map<String, M4804TranTypeVO> tranTypeMap) {
    super();
    this.tranTypeMap = tranTypeMap;
  }

  @Override
  public void process(DelivBillAggVO[] vos) {
    try {
      for (DelivBillAggVO vo : vos) {
        this.checkTranType(vo);
        this.checkBadvfeeflag(vo);
        this.checkbodysettlefinorg(vo);
      }
    }
    catch (Exception e) {
      ExceptionUtils.wrappException(e);
    }
  }

  /**
   * 
   * @param tranTypeMap
   */
  public void setTranTypeMap(Map<String, M4804TranTypeVO> tranTypeMap) {
    this.tranTypeMap = tranTypeMap;
  }

  private void checkTranType(DelivBillAggVO vo) {
    DelivBillHVO head = vo.getParentVO();
    // Ĭ��Ϊ��������������������¼û����Դidʱ��Ϊ������
    boolean addManual = false;
    // ��Ŀ�ֵ�������
    DelivBillBVO[] bodys = vo.getDelivBillBVO();
    if (bodys != null && bodys.length > 0) {
      for (DelivBillBVO body : bodys) {
        if (PubAppTool.isNull(body.getCfirstid())) {
          addManual = true;
          break;
        }
      }
    }
    // ��װ����Ϣֻ��ͨ���������ɣ����������û����Ϣ��ֻ�а�װ���еĻ������ݿ϶������Ƶ�
    boolean hasBodyBill =
        vo.getDelivBillBVO() != null && vo.getDelivBillBVO().length > 0;
    boolean hasBodyPack =
        vo.getDelivBillPackVO() != null && vo.getDelivBillPackVO().length > 0;
    if (!hasBodyBill && hasBodyPack) {
      addManual = true;
    }
    // ���Ƶĵ���
    if (addManual) {
      String ctrantypeCode = head.getVtrantypecode();
      M4804TranTypeVO tranType = this.tranTypeMap.get(ctrantypeCode);
      // ���Ƶ���ʱѡ��Ľ������Ͳ�֧������
      if (tranType.getBcanselfmadeflag() != null
          && tranType.getBcanselfmadeflag() == UFBoolean.FALSE) {
        String message =
            nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
                "04014001-0343")/*@res "��ѡ��Ľ������Ͳ�֧�����ƣ�"*/;
        ExceptionUtils.wrappBusinessException(message);
      }
    }
  }

  /**
   * ��������Ϊ����Ӧ���˷Ѽ�������䵥��������Ϊ�������
   * 
   * @param vo ���䵥
   */
  private void checkBadvfeeflag(DelivBillAggVO vo) {
    DelivBillHVO head = vo.getParentVO();
    String ctrantypeCode = head.getVtrantypecode();
    M4804TranTypeVO tranType = this.tranTypeMap.get(ctrantypeCode);
    if (tranType.getBcalculateapfeeflag().booleanValue()
        && head.getBadvfeeflag().booleanValue()) {
      String msg =
          NCLangResOnserver.getInstance().getStrByID("4014001_0",
              "04014001-0104")/*��ѡ��������Ϊ����Ӧ���˷Ѽ��㣬�����������ã�*/;
      ExceptionUtils.wrappBusinessException(msg);
    }
  }

  /**
   * ��ѡ��������Ϊ����Ӧ���˷Ѽ��㣬������������֯����Ϊ��
   * 
   * @param vo ���䵥
   */
  private void checkbodysettlefinorg(DelivBillAggVO vo) {
    DelivBillHVO head = vo.getParentVO();
    String ctrantypeCode = head.getVtrantypecode();
    M4804TranTypeVO tranType = this.tranTypeMap.get(ctrantypeCode);
    if (tranType.getBcalculateapfeeflag().booleanValue()) {
      for (DelivBillBVO bvo : vo.getDelivBillBVO()) {
        String csettlefinorgid = bvo.getCsettlefinorgid();
        if (csettlefinorgid == null) {
          String msg =
              NCLangResOnserver.getInstance().getStrByID("4014001_0",
                  "04014001-0105")/*��ѡ��������Ϊ����Ӧ���˷Ѽ��㣬������������֯����Ϊ�գ�*/;
          ExceptionUtils.wrappBusinessException(msg);
        }
      }
    }
  }
}
