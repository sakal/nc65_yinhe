/**
 * 
 */
package nc.itf.so.m30.self;

import nc.bs.bank_cvp.compile.registry.BussinessMethods;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.so.m30.entity.SaleOrderVO;

/**
 * ���۶�����������������ѯ�ӿ�
 * 
 * @author yuxx3
 * @created at 2016��4��27��,����3:37:14
 * 
 */
public interface ISaleOrderFor5X {

	public SaleOrderVO[] querySaleOrderFor5X(IQueryScheme queryscheme)
			throws BusinessException;

}
