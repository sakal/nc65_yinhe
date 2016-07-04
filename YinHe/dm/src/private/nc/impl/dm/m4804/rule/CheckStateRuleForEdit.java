package nc.impl.dm.m4804.rule;

import nc.impl.pubapp.pattern.rule.IRule;
import nc.vo.dm.enumeration.FstatusFlag;
import nc.vo.dm.m4804.entity.DelivBillAggVO;
import nc.vo.dm.m4804.entity.DelivBillHVO;
import nc.vo.pubapp.pattern.data.ValueUtils;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

/**
 * ��鵥��״̬
 * 
 * @since 6.0
 * @version 2010-11-22 ����03:21:15
 * @author yinyxa
 */
public class CheckStateRuleForEdit implements IRule<DelivBillAggVO> {

  @Override
  public void process(DelivBillAggVO[] vos) {
    for (DelivBillAggVO vo : vos) {
      DelivBillHVO hvo = vo.getParentVO();
      int flag = hvo.getFstatusflag().intValue();
      // �޸ļ��
      if (ValueUtils.getInt(FstatusFlag.FREE.value()) != flag) {
        ExceptionUtils
            .wrappBusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
                .getStrByID("4014001_0", "04014001-0341")/*@res "����Ϊ������̬�����ɱ༭��"*/);
      }
    }
  }
}
