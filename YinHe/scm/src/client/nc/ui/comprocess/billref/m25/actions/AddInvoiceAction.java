/**
 * 
 */
package nc.ui.comprocess.billref.m25.actions;

import java.awt.event.ActionEvent;

import nc.itf.uap.pf.busiflow.PfButtonClickContext;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.pubapp.billref.dest.TransferViewProcessor;
import nc.ui.pubapp.uif2app.actions.AbstractReferenceAction;
import nc.ui.uif2.UIState;
import nc.ui.uif2.editor.BillForm;
import nc.ui.uif2.model.AbstractAppModel;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.scmpub.res.billtype.POBillType;
import nc.vo.scmpub.res.billtype.TOBillType;


/**
 * �ɹ���Ʊ��ί�мӹ����
 * @author niangzi
 * @created at 2016��4��25��,����11:24:09
 *
 */
public class AddInvoiceAction extends AbstractReferenceAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5621343152306833927L;
	private BillForm editor;
	private AbstractAppModel model;
	private TransferViewProcessor processor;
	
	
	/**
	 * @create by niangzi at 2016��4��25��,����2:04:03
	 *
	 */
	public AddInvoiceAction() {
		// TODO �Զ����ɵĹ��캯�����
		this.setBtnName("ί�мӹ����");
		this.setCode("ref47");
	}
	/* ���� Javadoc��
	 * @see nc.ui.uif2.NCAction#doAction(java.awt.event.ActionEvent)
	 */
	
	
	@Override
	public void doAction(ActionEvent arg0) throws Exception {
		// TODO �Զ����ɵķ������
		PfUtilClient.childButtonClickedNew(createPfButtonClickContext());
		if (PfUtilClient.isCloseOK()) {
			AggregatedValueObject[] vos = PfUtilClient.getRetVos();
			if (vos == null) {
				return;
			}
			// �������ʾ��ת������
			this.getProcessor().processBillTransfer(vos);
		}
	}
	
	private PfButtonClickContext createPfButtonClickContext() {
		// TODO �Զ����ɵķ������
		PfButtonClickContext context = new PfButtonClickContext();
		context.setParent(this.getModel().getContext().getEntranceUI());
		context.setSrcBillType(this.getSourceBillType());
		context.setPk_group(this.getModel().getContext().getPk_group());
		context.setUserId(this.getModel().getContext().getPk_loginUser());
		// ����ýڵ����ɽ������ͷ����ģ���ô�������Ӧ�ô��������ͣ����򴫵�������
		context.setCurrBilltype(POBillType.Invoice.getCode());
		context.setUserObj(null);
		context.setSrcBillId(null);
		context.setBusiTypes(this.getBusitypes());
		// ����Ĳ�����ԭ�����õķ����ж����漰��ֻ���������һ�����ṹ�����������������¼ӵĲ���
		// ���εĽ������ͼ���
		context.setTransTypes(this.getTranstypes());
		// ��־�ڽ�������Ŀ�Ľ������ͷ���ʱ������Ŀ�Ľ������͵����ݣ�������������ֵ��1�����ݽӿڶ��壩��
		// 2�������������ã���-1�������ݽ������ͷ��飩
		context.setClassifyMode(PfButtonClickContext.ClassifyByBusiflow);
		return context;
	}


	/* ���� Javadoc��
	 * @see nc.ui.uif2.NCAction#isActionEnable()
	 */
	@Override
	protected boolean isActionEnable() {
		// TODO �Զ����ɵķ������
		return this.model.getUiState() == UIState.NOT_EDIT;
	}
	
	public BillForm getEditor() {
		return editor;
	}
	public void setEditor(BillForm editor) {
		this.editor = editor;
	}
	public AbstractAppModel getModel() {
		return model;
	}
	public void setModel(AbstractAppModel model) {
		this.model = model;
	}
	public TransferViewProcessor getProcessor() {
		return processor;
	}
	public void setProcessor(TransferViewProcessor processor) {
		this.processor = processor;
	}
	


}