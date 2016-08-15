/**
 * 
 */
package nc.ui.dm.m4804.editor.bodyevent;

import nc.ui.dm.m4804.handler.AddrdocEditHandler;
import nc.ui.dm.m4804.handler.AssistNumEditHandler;
import nc.ui.dm.m4804.handler.AstUnitEditHandler;
import nc.ui.dm.m4804.handler.BatchCodeHandler;
import nc.ui.dm.m4804.handler.BodyDelivDateEdit;
import nc.ui.dm.m4804.handler.ChangeRateEditHandler;
import nc.ui.dm.m4804.handler.CpackidHandler;
import nc.ui.dm.m4804.handler.CreceivecustHandler;
import nc.ui.dm.m4804.handler.CreceivestoreorgvHandler;
import nc.ui.dm.m4804.handler.CsendvendorHandler;
import nc.ui.dm.m4804.handler.InventoryEditHandler;
import nc.ui.dm.m4804.handler.MoneyEditHandler;
import nc.ui.dm.m4804.handler.NumberEditHandler;
import nc.ui.dm.m4804.handler.PacknumHandler;
import nc.ui.dm.m4804.handler.PackvolumeHandler;
import nc.ui.dm.m4804.handler.PackweightHandler;
import nc.ui.dm.m4804.handler.PriceEditHandler;
import nc.ui.dm.m4804.handler.ReceiveStoreEditHandler;
import nc.ui.dm.m4804.handler.SendStoreEditHandler;
import nc.ui.dm.m4804.handler.SendStoreOrgEditHandler;
import nc.ui.dm.m4804.handler.VolumnWeightHandler;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent;
import nc.ui.pubapp.util.CardPanelValueUtils;
import nc.vo.dm.m4804.entity.DelivBillAggVO;
import nc.vo.dm.m4804.entity.DelivBillBVO;
import nc.vo.dm.m4804.entity.DelivBillHVO;
import nc.vo.dm.m4804.entity.DelivBillPackVO;
import nc.vo.pub.lang.UFDouble;
/**
 * @author yuxx
 * @created at 2016-8-5,����5:01:35
 * 
 */
public class DelivBillBodyAfterEditHandler implements
		IAppEventHandler<CardBodyAfterEditEvent> {

	@Override
	public void handleAppEvent(CardBodyAfterEditEvent e) {
		BillCardPanel panel = e.getBillCardPanel();
	    CardPanelValueUtils util = new CardPanelValueUtils(panel);
	    int rowCount = util.getRowCount();
		// @edit by yuxx at 2016-8-5,����5:14:47 vdef15,vdef16,vdef17��������������������༭��
		if(DelivBillBVO.VBDEF18.equals(e.getKey())){
			//UFDouble totaldef18 = (UFDouble) e.getBillCardPanel().getTotalTableModel().getValueAt(0, 11);
			UFDouble totaldef18 = getTotal(util,DelivBillBVO.VBDEF18,rowCount);
			util.setHeadTailValue(DelivBillHVO.VDEF15, totaldef18);//����
		}
		if(DelivBillBVO.VBDEF19.equals(e.getKey())){
			//UFDouble totaldef19 = (UFDouble) e.getBillCardPanel().getTotalTableModel().getValueAt(0, 12);
			UFDouble totaldef19 = getTotal(util,DelivBillBVO.VBDEF19,rowCount);
			util.setHeadTailValue(DelivBillHVO.VDEF16, totaldef19);//����
		}
		if(DelivBillBVO.VBDEF20.equals(e.getKey())){
			//UFDouble totaldef20 = (UFDouble) e.getBillCardPanel().getTotalTableModel().getValueAt(0, 13);
			UFDouble totaldef20 = getTotal(util,DelivBillBVO.VBDEF20,rowCount);
			util.setHeadTailValue(DelivBillHVO.VDEF17, totaldef20);//���
		}
		//�����༭��ϼ�vdef18,vdef19,vdef20�������������������
		if(DelivBillBVO.NASTNUM.equals(e.getKey())){
		    // @edit by yuxx at 2016-8-5,����5:06:48 �ϼ�vdef18,vdef19,vdef20�������������������
			UFDouble totaldef18 = getTotal(util,DelivBillBVO.VBDEF18,rowCount);
			UFDouble totaldef19 = getTotal(util,DelivBillBVO.VBDEF19,rowCount);
			UFDouble totaldef20 = getTotal(util,DelivBillBVO.VBDEF20,rowCount);
		    util.setHeadTailValue(DelivBillHVO.VDEF15, totaldef18);//����
		    util.setHeadTailValue(DelivBillHVO.VDEF16, totaldef19);//����
		    util.setHeadTailValue(DelivBillHVO.VDEF17, totaldef20);//���
		    // @end edit
		}
		// @end edit
		// ������
		if (DelivBillBVO.NNUM.equals(e.getKey())) {

			NumberEditHandler handler = new NumberEditHandler();
			handler.afterEdit(e);
		}
		// ����
		else if (DelivBillBVO.NPRICE.equals(e.getKey())) {
			PriceEditHandler handler = new PriceEditHandler();
			handler.afterEdit(e);
		}
		// ���
		else if (DelivBillBVO.NMONEY.equals(e.getKey())) {
			MoneyEditHandler handler = new MoneyEditHandler();
			handler.afterEdit(e);
		}
		// ����
		else if (DelivBillBVO.CINVENTORYVID.equals(e.getKey())) {
			InventoryEditHandler handler = new InventoryEditHandler();
			handler.afterEdit(e);
		}
		// ��λ(����λ)
		else if (DelivBillBVO.CASTUNITID.equals(e.getKey())) {
			AstUnitEditHandler handler = new AstUnitEditHandler();
			handler.afterEdit(e);
		}
		// ������
		else if (DelivBillBVO.VCHANGERATE.equals(e.getKey())) {
			ChangeRateEditHandler handler = new ChangeRateEditHandler();
			handler.afterEdit(e);
		}
		// ������
		else if (DelivBillBVO.NASTNUM.equals(e.getKey())) {
			AssistNumEditHandler handler = new AssistNumEditHandler();
			handler.afterEdit(e);
		}
		// ���������֯
		else if (DelivBillBVO.CSENDSTOREORGVID.equals(e.getKey())) {
			SendStoreOrgEditHandler handler = new SendStoreOrgEditHandler();
			handler.afterEdit(e);
		}
		// ������Ӧ��
		else if (DelivBillBVO.CSENDVENDORID.equals(e.getKey())) {
			CsendvendorHandler handler = new CsendvendorHandler();
			handler.afterEdit(e);
		}
		// �����ֿ�
		else if (DelivBillBVO.CSENDSTOREID.equals(e.getKey())) {
			SendStoreEditHandler handler = new SendStoreEditHandler();
			handler.afterEdit(e);
		}
		// �ջ��ֿ�
		else if (DelivBillBVO.CRECEIVESTOREID.equals(e.getKey())) {
			ReceiveStoreEditHandler handler = new ReceiveStoreEditHandler();
			handler.afterEdit(e);
		}
		// �������ڡ�ʱ������
		else if (DelivBillBVO.DSENDDATE.equals(e.getKey())
				|| DelivBillBVO.DSENDTIME.equals(e.getKey())
				|| DelivBillBVO.DREQUIREDATE.equals(e.getKey())
				|| DelivBillBVO.DREQUIRETIME.equals(e.getKey())) {
			BodyDelivDateEdit handler = new BodyDelivDateEdit();
			handler.afterEdit(e);
		} else if (DelivBillPackVO.CPACKID.equals(e.getKey())) {
			CpackidHandler handler = new CpackidHandler();
			handler.afterEdit(e);
		}

		// �ջ������֯
		else if (DelivBillBVO.CRECEIVESTOREORGVID.equals(e.getKey())) {
			CreceivestoreorgvHandler handler = new CreceivestoreorgvHandler();
			handler.afterEdit(e);
		}

		// �ص㴦��
		else if (DelivBillBVO.CSENDADDRDOCID.equals(e.getKey())
				|| DelivBillBVO.CRECEIVEADDRDOCID.equals(e.getKey())) {
			AddrdocEditHandler handler = new AddrdocEditHandler();
			handler.afterEdit(e);
		}

		// �ջ��ͻ�
		else if (DelivBillBVO.CRECEIVECUSTID.equals(e.getKey())) {
			CreceivecustHandler editor = new CreceivecustHandler();
			editor.afterEdit(e);
		}
		// ��װ����
		else if (DelivBillPackVO.NPACKNUM.equals(e.getKey())) {
			PacknumHandler editor = new PacknumHandler();
			editor.afterEdit(e);
		}
		// ��װ����
		else if (DelivBillPackVO.NPACKVOLUME.equals(e.getKey())) {
			PackvolumeHandler editor = new PackvolumeHandler();
			editor.afterEdit(e);
		}
		// ��װ����
		else if (DelivBillPackVO.NPACKWEIGHT.equals(e.getKey())) {
			PackweightHandler editor = new PackweightHandler();
			editor.afterEdit(e);
		}
		// ���������
		else if (DelivBillBVO.NVOLUMN.equals(e.getKey())
				|| DelivBillBVO.NWEIGHT.equals(e.getKey())) {
			VolumnWeightHandler editor = new VolumnWeightHandler();
			editor.afterEdit(e);
		}
		// ���κ�
		else if (DelivBillBVO.VBATCHCODE.equals(e.getKey())) {
			// BatchCodeEditHandler editor = new BatchCodeEditHandler();
			// editor.afterEdit(e);
			BatchCodeHandler handler = new BatchCodeHandler(
					DelivBillAggVO.class, DelivBillBVO.class);
			handler.afterEdit(e);
		}
		/**
		 * ��ִ̨��ǰ̨vbillcode�ϵı༭��ʽ�������ۿۣ�����ۿ۹�ʽ�仯һ����Ҫ��vbillcode�ı༭��ʽ�и��ģ�������Ч
		 */
		String[] formula=e.getBillCardPanel().getHeadItem("vbillcode").getEditFormulas();
		try{
			e.getBillCardPanel().execHeadFormulas(formula);
		}catch(Exception ex){
			//���쳣�Ļ��ݲ�������ҪӰ��ǰ̨�ļ���
		}
		// else if (DelivBillPackVO.NPACKNUM.equals(e.getKey())) {
		// PacknumEditHandler editor = new PacknumEditHandler();
		// editor.afterEdit(e);
		// }
		// ������ַ
		// else if (DelivBillBVO.CSENDADDRID.equals(e.getKey())) {
		//
		// }
		// ���������֯
		// else if (e.getKey().equals(DelivBillBVO.CSENDSTOREORGVID)) {
		// SendStoreOrgEditHandler handler = new SendStoreOrgEditHandler();
		// handler.afterEdit(e);
		// }

		// �����ֿ�
		// else if (e.getKey().equals(DelivBillBVO.CSENDSTOREID)) {
		// SendStoreEditHandler handler = new SendStoreEditHandler();
		// handler.afterEdit(e);
		// }
	}
	// private void materialCanMoreSelected(CardBodyAfterEditEvent e) {
	// // ���϶�ѡ
	// RefMoreSelectedUtils moreSelectedUtils =
	// new RefMoreSelectedUtils(e.getBillCardPanel());
	// moreSelectedUtils.refMoreSelected(e.getRow(), e.getKey(), true);
	// }

	/**
	 * �ϼƼ��������������
	 * @create by yuxx at 2016-8-15,����10:47:34
	 *
	 * @param util
	 * @param vbdef
	 * @param rowCount
	 * @return
	 */
	private UFDouble getTotal(CardPanelValueUtils util, String vbdef,
			int rowCount) {
		UFDouble totalvdef = UFDouble.ZERO_DBL;
		UFDouble vdef = UFDouble.ZERO_DBL;
		if(rowCount>0){
			for(int i = 0;i<rowCount;i++){
				vdef = util.getBodyUFDoubleValue(i, vbdef);
				vdef = vdef==null ? UFDouble.ZERO_DBL:vdef;
				
				totalvdef = totalvdef.add(vdef);
			}
		}
		return totalvdef;
	}

}
