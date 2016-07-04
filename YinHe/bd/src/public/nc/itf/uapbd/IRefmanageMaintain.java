package nc.itf.uapbd;

import nc.md.model.MetaDataException;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.bd.cust.refmanage.RefManageVO;
import nc.vo.bd.meta.BatchOperateVO;
import nc.vo.pub.BusinessException;
import nc.vo.uif2.LoginContext;

/**
 * ��������Ͳ�ѯ�ӿ�
 * 
 * @author xx
 * 
 */
public interface IRefmanageMaintain {

	/**
	 * ��������
	 * 
	 * @param paramBatchOperateVO
	 * @return
	 * @throws BusinessException
	 */
	public abstract BatchOperateVO batchSave(
			BatchOperateVO paramBatchOperateVO) throws BusinessException;

	/**
	 * ��ѯ(���߹ܿ�ģʽ)
	 * @param paramLoginContext
	 * @param paramString
	 * @param paramBoolean
	 * @return
	 * @throws MetaDataException
	 * @throws BusinessException
	 */
	public abstract RefManageVO[] queryByDataVisibilitySetting(
			LoginContext paramLoginContext, String paramString,
			boolean paramBoolean) throws MetaDataException, BusinessException;

	public RefManageVO[] query(IQueryScheme queryScheme)
			throws BusinessException, Exception;
}