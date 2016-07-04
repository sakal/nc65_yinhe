/**
 * 
 */
package nc.ui.uapbd.refmanage.ace.handler;

import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyBeforeEditEvent;

/**
 * �����ֶα༭ǰ�¼�����Ҫ����Ʊ�ӳ��ʣ���������ʣ�Ӧ�տ����
 * 
 * @author yuxx3
 * @created at 2016��4��13��,����9:12:09
 * 
 */
public class AceBodyBeforeEditHandler implements
		IAppEventHandler<CardBodyBeforeEditEvent> {

	@Override
	public void handleAppEvent(CardBodyBeforeEditEvent e) {
		int selectindex = e.getBillCardPanel().getBillTable().getSelectedRow();
		Object busitype = e.getBillCardPanel().getBillModel()
				.getValueAt(selectindex, "busitype");
		if (busitype != null) {
			if (e.getKey().equals("billlatepunish")) {
				if (!busitype.equals("��Ʊ")) {
					e.setReturnValue(false);
				}
			} else if (e.getKey().equals("recoverycom")
					|| e.getKey().equals("recacountlate")) {
				if (!busitype.equals("�տ�")) {
					e.setReturnValue(false);
				}
			}
		}
		e.setReturnValue(true);
	}
}
