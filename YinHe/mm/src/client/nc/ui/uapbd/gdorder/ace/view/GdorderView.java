/**
 * 
 */
package nc.ui.uapbd.gdorder.ace.view;

import nc.ui.pubapp.uif2app.view.OrgPanel;

/**
 * ��֯����
 * @author niangzi
 * @created at 2016��5��30��,����3:51:25
 *
 */
public class GdorderView extends OrgPanel {
	private static final long serialVersionUID = 3262480715743501657L;
	
	@Override
	public void initUI() {
		// TODO �Զ����ɵķ������
		super.initUI();
		this.getDataManager().initModel();
	}
}
