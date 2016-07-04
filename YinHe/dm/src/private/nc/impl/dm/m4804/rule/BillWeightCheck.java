package nc.impl.dm.m4804.rule;

import java.util.HashMap;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.pubitf.scmf.dm.vehicle.IVehicleQueryService;
import nc.pubitf.scmf.dm.vehicletype.IVehicleTypeQueryService;
import nc.vo.dm.m4804.entity.DelivBillAggVO;
import nc.vo.dm.m4804trantype.entity.M4804TranTypeVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.scmf.dm.vehicle.entity.VehicleVO;
import nc.vo.scmf.dm.vehicletype.entity.VehicleTypeVO;

/**
 * ���䵥�ĳ��������
 * 
 * @since 6.0
 * @version 2010-11-22 ����04:13:07
 * @author yinyxa
 */
public class BillWeightCheck implements IRule<DelivBillAggVO> {

  private Map<String, M4804TranTypeVO> tranTypeMap =
      new HashMap<String, M4804TranTypeVO>();

  // ��������
  private Map<String, UFDouble> vehicleIndex = new HashMap<String, UFDouble>();

  // ������ѯ����
  private IVehicleQueryService vehicleQueryService;

  // ���ͻ���
  private Map<String, UFDouble> vehicleTypeIndex =
      new HashMap<String, UFDouble>();

  // ���Ͳ�ѯ����
  private IVehicleTypeQueryService vehicleTypeQueryService;

  public BillWeightCheck(Map<String, M4804TranTypeVO> tranTypeMap) {
    super();
    this.tranTypeMap = tranTypeMap;
  }

  public IVehicleQueryService getVehicleQueryService() {
    if (this.vehicleQueryService == null) {
      this.vehicleQueryService =
          NCLocator.getInstance().lookup(IVehicleQueryService.class);
    }
    return this.vehicleQueryService;
  }

  public IVehicleTypeQueryService getVehicleTypeQueryService() {
    if (this.vehicleTypeQueryService == null) {
      this.vehicleTypeQueryService =
          NCLocator.getInstance().lookup(IVehicleTypeQueryService.class);
    }
    return this.vehicleTypeQueryService;
  }

  @Override
  public void process(DelivBillAggVO[] vos) {
    for (DelivBillAggVO bill : vos) {
      this.processBillWeightCheck(bill);
    }
  }

  /**
   * У����ϸ��������������С������
   * 
   * @param bill
   * @param max
   */
  private void checkBillBody(DelivBillAggVO bill, UFDouble max) {
/*    DelivBillBVO[] vos = bill.getDelivBillBVO();
    if ((vos == null) || (vos.length == 0)) {
      return;
    }
    for (DelivBillBVO vo : vos) {
      if (vo.getStatus() == VOStatus.DELETED) {
        continue;
      }
      UFDouble nweight = vo.getNweight();
      UFDouble diff = MathTool.sub(max, nweight);
      if (diff.doubleValue() < 0) {
        String message =
            NCLangResOnserver.getInstance().getStrByID("4014001_0",
                "04014001-0048", null, new String[] {
                  nweight.toString(), max.toString()
                }) ������������{0}������ҪС�ڵ��ڳ��������͵����ء�{1}�� ;
        ExceptionUtils.wrappBusinessException(message);
      }
    }*/
    // ������
/*    UFDouble ntotalweight = bill.getParentVO().getNtotalweight();
    UFDouble diff = MathTool.sub(max, ntotalweight);
    if (diff.doubleValue() < 0) {
      String message =
          NCLangResOnserver.getInstance().getStrByID("4014001_0",
              "04014001-0049", null, new String[] {
                ntotalweight.toString(), max.toString()
              }) �����������ϼơ�{0}������ҪС�ڵ��ڳ��������͵����ء�{1}�� ;
      ExceptionUtils.wrappBusinessException(message);
    }*/
  }

  private void checkPackBody(DelivBillAggVO bill, UFDouble max) {
/*    DelivBillPackVO[] vos = bill.getDelivBillPackVO();
    if ((vos == null) || (vos.length == 0)) {
      return;
    }
    for (DelivBillPackVO vo : vos) {
      if (vo.getStatus() == VOStatus.DELETED) {
        continue;
      }
      UFDouble npackweight = vo.getNpackweight();
      UFDouble diff = MathTool.sub(max, npackweight);
      if (diff.doubleValue() < 0) {
        String message =
            NCLangResOnserver.getInstance().getStrByID("4014001_0",
                "04014001-0050", null, new String[] {
                  npackweight.toString(), max.toString()
                }) ��װ��������{0}������ҪС�ڵ��ڳ��������͵����ء�{1}�� ;
        ExceptionUtils.wrappBusinessException(message);
      }
    }
    // �ܰ�װ����
    UFDouble ntotalpackweight = bill.getParentVO().getNtotalpackweight();
    UFDouble diff = MathTool.sub(max, ntotalpackweight);
    if (diff.doubleValue() < 0) {
      String message =
          NCLangResOnserver.getInstance().getStrByID("4014001_0",
              "04014001-0051", null, new String[] {
                ntotalpackweight.toString(), max.toString()
              }) ��װ�������ϼơ�{0}������ҪС�ڵ��ڳ��������͵����ء�{1}�� ;
      ExceptionUtils.wrappBusinessException(message);
    }*/
  }

  /**
   * ��ȡ�������أ�����Ϊ��ʱȡ����
   * 
   * @param bill
   * @return
   */
  private UFDouble getMaxWeight(DelivBillAggVO bill) {
    UFDouble max = null;
    String cvehicleid = bill.getParentVO().getCvehicleid();
    String cvehicletypeid = bill.getParentVO().getCvehicletypeid();
    if (cvehicleid != null) {
      max = this.loadVehicleWight(cvehicleid);
    }
    else if (cvehicletypeid != null) {
      max = this.loadVehicleTypeWight(cvehicletypeid);
    }
    return max;
  }

  /**
   * ��ȡ��������
   * 
   * @param cvehicletypeid
   * @return
   */
  private UFDouble loadVehicleTypeWight(String cvehicletypeid) {
    UFDouble weight = null;
    try {
      if (this.vehicleTypeIndex.containsKey(cvehicletypeid)) {
        return this.vehicleTypeIndex.get(cvehicletypeid);
      }
      VehicleTypeVO vehicleTypeVO =
          this.getVehicleTypeQueryService().getVehicleType(cvehicletypeid);
      if (vehicleTypeVO != null) {
        weight = vehicleTypeVO.getNload();
        this.vehicleTypeIndex.put(cvehicletypeid, weight);
      }
    }
    catch (Exception e) {
      ExceptionUtils.wrappException(e);
    }
    return weight;
  }

  /**
   * ��ȡ��������
   * 
   * @param cvehicleid
   * @return
   */
  private UFDouble loadVehicleWight(String cvehicleid) {
    UFDouble weight = null;
    try {
      if (this.vehicleIndex.containsKey(cvehicleid)) {
        return this.vehicleIndex.get(cvehicleid);
      }
      VehicleVO vehicleVO =
          this.getVehicleQueryService().getVehicleVo(cvehicleid);
      if (vehicleVO != null) {
        weight = vehicleVO.getNload();
        this.vehicleIndex.put(cvehicleid, weight);
      }
    }
    catch (Exception e) {
      ExceptionUtils.wrappException(e);
    }
    return weight;
  }

  private void processBillWeightCheck(DelivBillAggVO bill) {
/*    try {
      String tranTypeCode = bill.getParentVO().getVtrantypecode();
      if (StringUtils.isEmpty(tranTypeCode)) {
        return;
      }

      M4804TranTypeVO typeVO = this.tranTypeMap.get(tranTypeCode);

      // ���������ϲ���Ҫ���������
      boolean noCheck =
          (typeVO == null) || (typeVO.getBcheckweightflag() == null)
              || !typeVO.getBcheckweightflag().booleanValue();
      if (noCheck) {
        return;
      }

      UFDouble max = this.getMaxWeight(bill);
      if (max == null) {
        return;
      }
      this.checkBillBody(bill, max);
      this.checkPackBody(bill, max);
    }
    catch (Exception e) {
      ExceptionUtils.wrappException(e);
    }*/
  }

}
