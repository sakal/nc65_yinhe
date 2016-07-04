package nc.ui.uapbd.refmanage.action;

import nc.ui.pubapp.uif2app.actions.batch.BatchAddLineAction;
import nc.vo.bd.cust.refmanage.RefManageVO;

/**
 * �б�̬������ť
 * @author xx
 * @created at 2016��4��8��,����11:13:28
 *
 */
public class RefmanageAddLineAction extends BatchAddLineAction {

	private static final long serialVersionUID = 1L;
	

	@Override
	protected void setDefaultData(Object obj) {
		super.setDefaultData(obj);
		RefManageVO singleDocVO = (RefManageVO) obj;
		singleDocVO.setPk_group(this.getModel().getContext().getPk_group());
		singleDocVO.setPk_org(this.getModel().getContext().getPk_org());
	}

}