package nc.ui.uapbd.refmanage.ace.handler;

import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent;
import nc.vo.bd.cust.refmanage.RefManageVO;

public class AceBodyAfterEditHandler implements
		IAppEventHandler<CardBodyAfterEditEvent> {

	@Override
	public void handleAppEvent(CardBodyAfterEditEvent e) {
		// ҵ�������ֶα༭���¼�
		if (e.getKey().equals("busitype")) {
			int selectindex = e.getBillCardPanel().getBillTable()
					.getSelectedRow();
			// ҵ�������ǿ�Ʊʱ����Ʊ�ӳٷ��ʿɱ༭
			e.getBillCardPanel()
					.getBillModel()
					.setCellEditable(selectindex, "billlatepunish",
							e.getValue().equals("1"));
			e.getBillCardPanel()
					.getBillModel()
					.setCellEditable(selectindex, "recoverycom",
							e.getValue().equals("2"));
			e.getBillCardPanel()
					.getBillModel()
					.setCellEditable(selectindex, "recacountlate",
							e.getValue().equals("2"));

			// ������༭�Ժ�Ҫ��ձ�׼�·����������ڴ���������Ԥ����������Ʊ�ӳ��ʡ���������ʡ�Ӧ�տ����
			e.getBillCardPanel().getBillModel()
					.setValueAt(null, selectindex, "monthdaynum");
			e.getBillCardPanel().getBillModel()
					.setValueAt(null, selectindex, "punishdelaym");
			e.getBillCardPanel().getBillModel()
					.setValueAt(null, selectindex, "alertdays");
			e.getBillCardPanel().getBillModel()
					.setValueAt(0, selectindex, "billlatepunish");
			e.getBillCardPanel().getBillModel()
					.setValueAt(0, selectindex, "recoverycom");
			e.getBillCardPanel().getBillModel()
					.setValueAt(0, selectindex, "recacountlate");
		}
	}
}
