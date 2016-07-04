package nc.bs.dm.m4804;

import java.util.Map;

import nc.vo.dm.m4804.entity.DelivBillAggVO;
import nc.vo.dm.m4804.entity.DelivBillHVO;
import nc.vo.dm.m4804trantype.entity.M4804TranTypeVO;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.scmpub.res.billtype.DMBillType;
import nc.vo.scmpub.rule.TrafficOrgEnableCheckRule;

import nc.bs.dm.m4804.pub.CachTranTypeUitl;
import nc.bs.scmpub.app.flow.billcode.BillCodeInfoBuilder;
import nc.bs.scmpub.rule.DeleteTempDataBeforeRule;

import nc.impl.dm.m4804.rule.BillDataCheck;
import nc.impl.dm.m4804.rule.BillDataFill;
import nc.impl.dm.m4804.rule.BillWeightCheck;
import nc.impl.dm.m4804.rule.CheckUnique;
import nc.impl.dm.m4804.rule.CurrencyInfoCheck;
import nc.impl.dm.m4804.rule.DelivNumAddOperator;
import nc.impl.dm.m4804.rule.RouteInfoCalculate;
import nc.impl.dm.m4804.rule.SetAddAuditInfoRule;
import nc.impl.dm.m4804.rule.TotalInfoCalculate;
import nc.impl.dm.m4804.rule.TrantypeValidation;
import nc.impl.pubapp.bill.billcode.BillCodeInfo;
import nc.impl.pubapp.bill.billcode.BillCodeUtils;
import nc.impl.pubapp.pattern.data.bill.BillInsert;
import nc.impl.pubapp.pattern.rule.IRule;

public class DelivBillInsertBP {

  public DelivBillAggVO[] insert(DelivBillAggVO[] bills) {

    // ����ǰ����
    this.processBeforeRule(bills);
    BillInsert<DelivBillAggVO> billinsert = new BillInsert<DelivBillAggVO>();
    DelivBillAggVO[] ret = billinsert.insert(bills);
    // ���������
    this.processAfterRule(ret);
    return ret;
  }

  private void insertBillCode(DelivBillAggVO[] bills) {
    BillCodeInfo info =
        BillCodeInfoBuilder.buildBillCodeInfo(DMBillType.DelivBill.getCode(),
            DelivBillHVO.VBILLCODE, DelivBillHVO.PK_GROUP, DelivBillHVO.PK_ORG,
            DelivBillHVO.VTRANTYPECODE);
    BillCodeUtils util = new BillCodeUtils(info);
    util.createBillCode(bills);
  }

  private void processAfterRule(DelivBillAggVO[] bills) {
    // ���ݺ�Ψһ��У��
    IRule<DelivBillAggVO> rule = new CheckUnique();
    rule.process(bills);
  }

  private void processBeforeRule(DelivBillAggVO[] bills) {
    Map<String, M4804TranTypeVO> tranTypeVos =
        new CachTranTypeUitl().getTranTypesByBills(bills);

    // ���ɵ��ݺ�
    this.insertBillCode(bills);

    // ���ݱ���ϼƱ�ͷ�ֶ�
    IRule<DelivBillAggVO> rule = new TotalInfoCalculate();
    rule.process(bills);

    // �������
    rule = new BillDataFill();
    rule.process(bills);
    // ���ݼ��
    rule = new BillDataCheck(tranTypeVos);
    rule.process(bills);
    // ���䵥��������У��
    rule = new TrantypeValidation(tranTypeVos);
    rule.process(bills);
    // ���������
    //rule = new BillWeightCheck(tranTypeVos);
    //rule.process(bills);

    // ��������
    //rule = new BillVolumeCheck(tranTypeVos);
    //rule.process(bills);

    // ����·�������Ϣ�Ĵ���
    rule = new RouteInfoCalculate();
    rule.process(bills);

    // ��λ���֣������װ��Ϣ�ͻ�����Ϣ�Ľ��������֯�ͱ�ͷ��������֯���Ӧ�ı�λ������ͬ��
    rule = new CurrencyInfoCheck();
    rule.process(bills);

    // ��������ʱ�������Ϣ�������ˡ��Ƶ��ˡ��Ƶ�ʱ��
    rule = new SetAddAuditInfoRule();
    rule.process(bills);

    // �������䵥������д���ε���
    rule = new DelivNumAddOperator();
    rule.process(bills);

    // ����֯ͣ�ü�����
    rule = new TrafficOrgEnableCheckRule<DelivBillAggVO>();
    rule.process(bills);

    // ����ɾ���ݴ�����
    IRule<AbstractBill> rule1 = new DeleteTempDataBeforeRule();
    rule1.process(bills);

  }

}
