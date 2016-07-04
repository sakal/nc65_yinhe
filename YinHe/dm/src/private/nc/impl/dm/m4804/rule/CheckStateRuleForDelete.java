package nc.impl.dm.m4804.rule;

import nc.vo.dm.enumeration.FstatusFlag;
import nc.vo.dm.m4804.entity.DelivBillAggVO;
import nc.vo.dm.m4804.entity.DelivBillHVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pubapp.pattern.data.ValueUtils;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

import nc.impl.pubapp.pattern.rule.IRule;

/**
 * ��鵥��״̬
 * 
 * @since 6.0
 * @version 2010-11-22 ����03:21:15
 * @author yinyxa
 */
public class CheckStateRuleForDelete implements IRule<DelivBillAggVO> {

  @Override
  public void process(DelivBillAggVO[] vos) {
    try {
      for (DelivBillAggVO vo : vos) {
        DelivBillHVO hvo = vo.getParentVO();
        int flag = hvo.getFstatusflag().intValue();
        // �޸ļ��
        if (ValueUtils.getInt(FstatusFlag.FREE.value()) != flag) {
          ExceptionUtils.wrappBusinessException(nc.vo.ml.NCLangRes4VoTransl
              .getNCLangRes().getStrByID("4014001_0", "04014001-0340")
          /*@res "����Ϊ������̬������ɾ����"*/);
        }
        // У�����䵥�Ƿ�����������񵥣���Ϊ�ǵ�����ɾ����������������У�飬
        if (hvo.getCdelivbill_hid() != null) {
          UFBoolean bmissionbillflag = hvo.getBmissionbillflag();
          boolean result = false;
          if (bmissionbillflag != null
              && bmissionbillflag.equals(UFBoolean.TRUE)) {
            result = true;
          }
          if (result) {
            ExceptionUtils.wrappBusinessException(nc.vo.ml.NCLangRes4VoTransl
                .getNCLangRes().getStrByID("4014001_0", "04014001-0080")
            /*@res "���ݴ����������񵥣�����ɾ����"*/);
          }
        }
      }
    }
    catch (Exception e) {
      ExceptionUtils.wrappException(e);
    }
  }
}
