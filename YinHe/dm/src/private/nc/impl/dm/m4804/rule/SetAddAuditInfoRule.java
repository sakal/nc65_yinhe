package nc.impl.dm.m4804.rule;

import nc.impl.pubapp.pattern.rule.IRule;
import nc.vo.dm.m4804.entity.DelivBillAggVO;
import nc.vo.dm.m4804.entity.DelivBillHVO;
import nc.vo.pub.ISuperVO;
import nc.vo.pubapp.AppContext;
import nc.vo.pubapp.pattern.model.entity.bill.IBill;
import nc.vo.pubapp.util.AuditInfoUtils;

/**
 * ��������ʱ�������Ϣ�������ˡ��Ƶ��ˡ��Ƶ�ʱ��
 * 
 * @since 6.0
 * @version 2011-3-8 ����06:33:07
 * @author ����
 */
public class SetAddAuditInfoRule implements IRule<DelivBillAggVO> {

  @Override
  public void process(DelivBillAggVO[] vos) {
    AuditInfoUtils.setAddAuditInfo(vos);
    ISuperVO vo = null;
    for (IBill bill : vos) {
      vo = bill.getParent();
      // �Ƶ���
      vo.setAttributeValue(DelivBillHVO.BILLMAKER, AppContext.getInstance()
          .getPkUser());
      // �Ƶ�����
      vo.setAttributeValue(DelivBillHVO.DMAKEDATE, AppContext.getInstance()
          .getBusiDate());
    }
  }
}
