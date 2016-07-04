package nc.impl.uapbd;

import nc.impl.ambd.db.QueryUtil;
import nc.impl.pubapp.pub.smart.BatchSaveAction;
import nc.itf.uapbd.IRefmanageMaintain;
import nc.md.model.MetaDataException;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.bd.cust.refmanage.RefManageVO;
import nc.vo.bd.meta.BatchOperateVO;
import nc.vo.pub.BusinessException;
import nc.vo.uif2.LoginContext;
import nc.vo.util.VisibleUtil;

/**
 * ����Ͳ�ѯ����
 * @author xx
 * @created at 2016��4��8��,����11:22:03
 *
 */
public class RefmanageMaintainImpl implements IRefmanageMaintain {

	@Override
	public RefManageVO[] query(IQueryScheme queryScheme)
			throws BusinessException {
		return null;
	}

	@Override
	public BatchOperateVO batchSave(BatchOperateVO batchVO)
			throws BusinessException {
		BatchSaveAction<RefManageVO> saveAction = new BatchSaveAction<RefManageVO>();
		
		BatchOperateVO retData = saveAction.batchSave(batchVO);
		return retData;
	}

	@Override
	public RefManageVO[] queryByDataVisibilitySetting(
			LoginContext paramLoginContext, String paramString,
			boolean paramBoolean) throws MetaDataException, BusinessException {
		/*StringBuffer whereCondition = new StringBuffer(
				VisibleUtil.getVisibleCondition(paramLoginContext,
						RefManageVO.class));*/
		// @edit by xx at 2016��4��8��,����11:23:40   
		//���߹ܿ�ģʽ
		StringBuffer whereCondition = new StringBuffer("");
		if (paramString != null) {
			whereCondition.append(paramString);
		}
		RefManageVO[] refVOCollection = (RefManageVO[]) QueryUtil
				.queryVOByCond(RefManageVO.class, whereCondition.toString(),
						" order by ts");
		return refVOCollection;
	}
}
