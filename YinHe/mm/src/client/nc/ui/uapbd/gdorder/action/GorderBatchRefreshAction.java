/**
 * 
 */
package nc.ui.uapbd.gdorder.action;

import java.awt.event.ActionEvent;

import nc.ui.pubapp.uif2app.actions.batch.BatchRefreshAction;

/**
 * @author niangzi
 * @created at 2016��5��31��,����9:25:29
 *
 */
public class GorderBatchRefreshAction extends BatchRefreshAction {

	private static final long serialVersionUID = 5490380441188122553L;
	
	@Override
	public void doAction(ActionEvent e) throws Exception {
		// TODO �Զ����ɵķ������
		super.doAction(e);
		this.getModelManager().initModel();
	}
}
