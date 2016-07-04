package nc.impl.dm.m4804.rule;

import nc.impl.pubapp.pattern.rule.IRule;
import nc.vo.dm.m4804.entity.DelivBillAggVO;
import nc.vo.pubapp.util.AuditInfoUtils;

/**
 * �����޸�ʱ�������Ϣ�������ˡ��Ƶ��ˡ��Ƶ�ʱ��
 * 
 * @since 6.0
 * @version 2011-3-8 ����06:33:07
 * @author ����
 */
public class SetUpdateAuditInfoRule implements IRule<DelivBillAggVO> {

  @Override
  public void process(DelivBillAggVO[] vos) {
    AuditInfoUtils.setUpdateAuditInfo(vos);
  }

}
