package nc.bs.dm.m4804.pub;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nc.vo.dm.m4804.entity.DelivBillAggVO;
import nc.vo.dm.m4804trantype.entity.M4804TranTypeVO;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

import nc.itf.dm.m4804trantype.IM4804TranTypeService;

import nc.bs.framework.common.NCLocator;

/**
 * ���䵥�ദ��Ҫ���ݽ�������У�������Ϣ���������ڸ��ݾۺ�vo����õ����Ӧ��У��������Ϣ
 * 
 * @since 6.0
 * @version 2012-3-27 ����4:36:05
 * @author ����
 */
public class CachTranTypeUitl {

  /**
   * �������䵥�ۺ�vo�����ѯ��Ҫ��Ҫ�õ���У��������Ϣ
   * 
   * @param vos ���䵥�ۺ�vo����
   * @return Map<String, M4804TranTypeVO>
   *         key:��������code�� value����Ӧ�Ľ���������Ϣ
   */
  public Map<String, M4804TranTypeVO> getTranTypesByBills(DelivBillAggVO[] vos) {
    Map<String, M4804TranTypeVO> result = null;
    Set<String> ids = new HashSet<String>();
    String pk_group = vos[0].getParentVO().getPk_group();
    for (DelivBillAggVO vo : vos) {
      if (vo.getParentVO().getVtrantypecode() == null) {
        continue;
      }
      ids.add(vo.getParentVO().getVtrantypecode());
    }
    try {
      result =
          NCLocator.getInstance().lookup(IM4804TranTypeService.class)
              .queryTranType(ids.toArray(new String[ids.size()]), pk_group);
    }
    catch (BusinessException e) {
      ExceptionUtils.wrappException(e);
    }
    return result;

  }
}
