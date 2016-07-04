package nc.impl.dm.m4804.rule;

import java.util.Map;

import nc.vo.dm.m4804.entity.DelivBillAggVO;
import nc.vo.dm.m4804.entity.DelivBillBVO;
import nc.vo.dm.m4804.entity.DelivBillHVO;
import nc.vo.dm.m4804.entity.DelivBillPackVO;
import nc.vo.dm.m4804trantype.entity.M4804TranTypeVO;
import nc.vo.dm.m4804trantype.enumeration.FAllocateScopeFlag;
import nc.vo.pub.lang.UFDate;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.pub.PubAppTool;
import nc.vo.scmpub.util.VOFieldLengthChecker;

import nc.bs.ml.NCLangResOnserver;

import nc.impl.pubapp.pattern.rule.IRule;

/**
 * ���䵥�����ݼ��
 * <ul>
 * <li>����ҵ��У��</li>
 * <li>����ֵ����</li>
 * </ul>
 * 
 * @since 6.0
 * @version 2010-11-19 ����02:50:07
 * @author yinyxa
 */
public class BillDataCheck implements IRule<DelivBillAggVO> {

  private Map<String, M4804TranTypeVO> tranTypeVos;

  /**
   * ��ʼ�������д��뽻������vo
   * 
   * @param tranTypeVo
   */
  public BillDataCheck(Map<String, M4804TranTypeVO> tranTypeVo) {
    super();
    this.tranTypeVos = tranTypeVo;
  }

  @Override
  public void process(DelivBillAggVO[] vos) {
    for (DelivBillAggVO bill : vos) {
      this.processBillDataCheck(bill);
    }
    // ����ֵУ��
    VOFieldLengthChecker.checkVOFieldsLength(vos);
  }

  private void checkBillBody(DelivBillHVO header, DelivBillBVO[] items) {
    try {
      String tranTypeCode = header.getVtrantypecode();
      M4804TranTypeVO typeVO = this.tranTypeVos.get(tranTypeCode);
      if (items == null || items.length == 0) {
        if (FAllocateScopeFlag.INV.equalsValue(typeVO.getFallocatescopeflag())) {
          String message =
              nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
                  "4014001_0", "04014001-0328")
          /* @res "��ǰ��������Ҫ���˷Ѽ�����̯Ŀ��Ϊ�����У�����Ҫ�л�������Ϣ"*/;
          ExceptionUtils.wrappBusinessException(message);
        }
        return;
      }
      int index = 1;
      for (DelivBillBVO item : items) {
        this.checkBillBodyRow(item, index++);

      }
    }
    catch (Exception e) {
      ExceptionUtils.wrappException(e);
    }
  }

  private void checkBillBodyRow(DelivBillBVO item, int i) {
    if (item.getNnum() == null) {
      String message =
          NCLangResOnserver.getInstance().getStrByID("4014001_0",
              "04014001-0024", null, new String[] {
                Integer.toString(i)
              })/* �����{0}������������Ϊ�� */;
      ExceptionUtils.wrappBusinessException(message);
    }
    // if (item.getCsendaddrdocid() == null) {
    // String message =
    // NCLangResOnserver.getInstance().getStrByID("4014001_0",
    // "04014001-0025", null, new String[] {
    // Integer.toString(i)
    // })/* �����{0}�з����ص㲻��Ϊ�� */;
    // ExceptionUtils.wrappBusinessException(message);
    // }
    // if (item.getCreceiveaddrdocid() == null) {
    // String message =
    // NCLangResOnserver.getInstance().getStrByID("4014001_0",
    // "04014001-0026", null, new String[] {
    // Integer.toString(i)
    // })/* �����{0}���ջ��ص㲻��Ϊ�� */;
    // ExceptionUtils.wrappBusinessException(message);
    // }
    String ctakefeeid = item.getCtakefeeid();
    String ccosignid = item.getCcosignid();
    this.checkTakeFeerAndCosigner(ctakefeeid, ccosignid);
  }

  private void checkPackBody(DelivBillHVO header, DelivBillPackVO[] items) {
    try {
      String tranTypeCode = header.getVtrantypecode();
      M4804TranTypeVO typeVO = this.tranTypeVos.get(tranTypeCode);
      if (items == null || items.length == 0) {
        if (FAllocateScopeFlag.PACK.equalsValue(typeVO.getFallocatescopeflag())) {
          String message =
              nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
                  "4014001_0", "04014001-0329")
          /* @res "��ǰ��������Ҫ���˷Ѽ�����̯Ŀ��Ϊ��װ�У�����Ҫ�а�װ����Ϣ"*/;
          ExceptionUtils.wrappBusinessException(message);
        }
        return;
      }
      for (DelivBillPackVO item : items) {
        String ctakefeeid = item.getCtakefeeid();
        String ccosignid = item.getCcosignid();
        this.checkTakeFeerAndCosigner(ctakefeeid, ccosignid);
      }
    }
    catch (Exception e) {
      ExceptionUtils.wrappException(e);
    }
  }

  /**
   * У��ί�е�λ���˷ѳе���λ
   * 
   * @param ctakefeeid
   * @param ccosignid
   */
  private void checkTakeFeerAndCosigner(String ctakefeeid, String ccosignid) {
    if (ccosignid != null && ctakefeeid == null) {
      String message =
          nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
              "04014001-0330")/* @res "ί�е�λ�ǿ�ʱ����Ҫ���˷ѳе���λ"*/;
      ExceptionUtils.wrappBusinessException(message);
    }
    else if (ctakefeeid != null && ccosignid == null) {
      String message =
          nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
              "04014001-0331")/* @res "�˷ѳе���λ�ǿ�ʱ����Ҫ��ί�е�λ"*/;
      ExceptionUtils.wrappBusinessException(message);
    }
  }

  private void checkTranType(DelivBillAggVO bill) {
    String tranTypeCode = bill.getParentVO().getVtrantypecode();
    M4804TranTypeVO tranType = this.tranTypeVos.get(tranTypeCode);
    Integer allocatescopeflag = tranType.getFallocatescopeflag();

    if (FAllocateScopeFlag.INV.equalsValue(allocatescopeflag)) {
      if (bill.getDelivBillBVO() == null || bill.getDelivBillBVO().length == 0) {
        String message =
            nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
                "04014001-0332")/* @res "���������˷ѷֵ�Ŀ��λ�����У���¼���������Ϣ��"*/;
        ExceptionUtils.wrappBusinessException(message);
      }
    }
    else if (FAllocateScopeFlag.PACK.equalsValue(allocatescopeflag)) {
      if (bill.getDelivBillPackVO() == null
          || bill.getDelivBillPackVO().length == 0) {
        String message =
            nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
                "04014001-0333")/* @res "���������˷ѷֵ�Ŀ��λ��װ�У���¼���װ����Ϣ��"*/;
        ExceptionUtils.wrappBusinessException(message);
      }
    }
  }

  private void processBillDataCheck(DelivBillAggVO bill) {
    DelivBillHVO header = bill.getParentVO();
    UFDate dbilldate = header.getDbilldate();
    UFDate ddelivdate = header.getDdelivdate();
    if (dbilldate == null) {
      String message =
          nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
              "04014001-0334")/* @res "�Ƶ����ڲ���Ϊ��"*/;
      ExceptionUtils.wrappBusinessException(message);
    }
    else if (ddelivdate == null) {
      String message =
          nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
              "04014001-0335")/* @res "�������ڲ���Ϊ��"*/;
      ExceptionUtils.wrappBusinessException(message);
    }
    else if (ddelivdate.beforeDate(dbilldate)) {
      String message =
          nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
              "04014001-0336")/* @res "�������ڲ���С�ڵ�������"*/;
      ExceptionUtils.wrappBusinessException(message);
    }
    // else if (header.getTdelivtime() != null
    // && !ddelivdate.isSameDate(header.getTdelivtime().getDate())) {
    // String message = "����ʱ������ں��������ڲ���";
    // ExceptionUtils.wrappBusinessException(message);
    // }
    else if (PubAppTool.isNull(header.getCtrantypeid())) {
      String message =
          nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
              "04014001-0337")/* @res "�������Ͳ���Ϊ��"*/;
      ExceptionUtils.wrappBusinessException(message);
    }
    // else if (header.getCrouteid() == null) {
    // String message =
    // nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
    // "04014001-0338")/* @res "����·�߲���Ϊ��"*/;
    // ExceptionUtils.wrappBusinessException(message);
    // }
    else if (header.getCsendtypeid() == null) {
      String message =
          nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
              "04014001-0339")/* @res "���䷽ʽ����Ϊ��"*/;
      ExceptionUtils.wrappBusinessException(message);
    }
    this.checkTranType(bill);

    DelivBillBVO[] billItems = bill.getDelivBillBVO();
    this.checkBillBody(header, billItems);
    DelivBillPackVO[] packItems = bill.getDelivBillPackVO();
    this.checkPackBody(header, packItems);
  }

}
