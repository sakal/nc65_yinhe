/**
 * 
 */
package nc.ui.uapbd.refmanage.action;

import nc.ui.pubapp.uif2app.actions.batch.BatchSaveAction;
import nc.ui.uif2.UIState;
import nc.vo.bd.cust.refmanage.RefManageVO;

/**
 * @author xx
 * @created at 2016��4��8��,����11:28:58
 * 
 */
public class RefManageSaveAction extends BatchSaveAction {
	
	private static final long serialVersionUID = -2139634900301272610L;

	@Override
	protected void beforeDoAction() {
		super.beforeDoAction();
		// @edit by xx at
		// 2016��4��8��,����1:02:14--�༭̬�����س�ֱ�ӵ������ᵼ�½�������δˢ�µ�model�У�ȡ������������
		//getModel().setUiState(UIState.NOT_EDIT);
		String user = getModel().getContext().getPk_loginUser();
		nc.vo.pub.lang.UFDateTime time2=nc.ui.pub.ClientEnvironment.getServerTime();
		Object[] addvos = getModel().getCurrentSaveObject().getAddObjs();
		for (Object obj : addvos) {
			((RefManageVO) obj).setCreator(user);
			((RefManageVO) obj).setCreationtime(time2);
		}
		Object[] updatevos = getModel().getCurrentSaveObject().getUpdObjs();
		for (Object obj : updatevos) {
			((RefManageVO) obj).setModifier(user);
			((RefManageVO) obj).setModifiedtime(time2);
		}
	}
}
