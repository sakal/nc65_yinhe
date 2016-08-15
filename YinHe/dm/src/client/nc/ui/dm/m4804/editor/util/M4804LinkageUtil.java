/**
 * 
 */
package nc.ui.dm.m4804.editor.util;

import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pubapp.util.CardPanelValueUtils;
import nc.vo.dm.m4804.entity.DelivBillBVO;
import nc.vo.dm.m4804.entity.DelivBillHVO;
import nc.vo.dm.m4804.entity.DelivBillPackVO;
import nc.vo.pub.lang.UFDouble;

/**
 * @author yuxx
 * @created at 2016-8-6,����2:27:21
 * 
 */
public class M4804LinkageUtil {

	private BillCardPanel billCardPanel;

	private int flag;

	/**
	 * ���䵥����ϼ��ֶ����������๹�캯��
	 * 
	 * @param billCardPanel
	 * @param flag
	 *            ����ҳǩflag=0 ��װҳǩflag=1
	 */
	public M4804LinkageUtil(BillCardPanel billCardPanel, int flag) {
		super();
		this.billCardPanel = billCardPanel;
		this.setFlag(flag);
	}

	public BillCardPanel getBillCardPanel() {
		return this.billCardPanel;
	}

	public int getFlag() {
		return this.flag;
	}

	public void linkage() {
		if (this.flag == 0) {
			this.linkageBill();
		} else if (this.flag == 1) {
			this.linkagePack();
		}

	}

	public void setBillCardPanel(BillCardPanel billCardPanel) {
		this.billCardPanel = billCardPanel;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	private void linkageBill() {
		CardPanelValueUtils util = new CardPanelValueUtils(this.billCardPanel);
		// ���к�
		int rowCount = util.getRowCount();
		if (rowCount > 0) {
			// ������
			UFDouble ntotalastnum = UFDouble.ZERO_DBL;
			// ��������
			UFDouble ntotalnum = UFDouble.ZERO_DBL;
			// ������
			UFDouble ntotalweight = UFDouble.ZERO_DBL;
			// �����
			UFDouble ntotalvolume = UFDouble.ZERO_DBL;
			// �ܽ��
			UFDouble ntotalmny = UFDouble.ZERO_DBL;
			
			// @edit by yuxx at 2016-8-15,����9:47:02 �ܼ����������������vdef15,vdef16,vdef17��
			UFDouble vdef15 = UFDouble.ZERO_DBL;
			UFDouble vdef16 = UFDouble.ZERO_DBL;
			UFDouble vdef17 = UFDouble.ZERO_DBL;
			//end edit
			
			// ����
			UFDouble nastnum = UFDouble.ZERO_DBL;
			// ������
			UFDouble nnum = UFDouble.ZERO_DBL;
			// ����
			UFDouble nweight = UFDouble.ZERO_DBL;
			// ���
			UFDouble nvolume = UFDouble.ZERO_DBL;
			// ���
			UFDouble nmny = UFDouble.ZERO_DBL;
			
		    // @edit by yuxx at 2016-8-15,����9:50:25 ������������������
			UFDouble vbdef18 = UFDouble.ZERO_DBL;
			UFDouble vbdef19 = UFDouble.ZERO_DBL;
			UFDouble vbdef20 = UFDouble.ZERO_DBL;
			// end edit
			for (int i = 0; i < rowCount; i++) {
				nastnum = util.getBodyUFDoubleValue(i, DelivBillBVO.NASTNUM);
				nastnum = nastnum == null ? UFDouble.ZERO_DBL : nastnum;
				nnum = util.getBodyUFDoubleValue(i, DelivBillBVO.NNUM);
				nnum = nnum == null ? UFDouble.ZERO_DBL : nnum;
				nweight = util.getBodyUFDoubleValue(i, DelivBillBVO.NWEIGHT);
				nweight = nweight == null ? UFDouble.ZERO_DBL : nweight;
				nvolume = util.getBodyUFDoubleValue(i, DelivBillBVO.NVOLUMN);
				nvolume = nvolume == null ? UFDouble.ZERO_DBL : nvolume;
				nmny = util.getBodyUFDoubleValue(i, DelivBillBVO.NMONEY);
				nmny = nmny == null ? UFDouble.ZERO_DBL : nmny;
				
				// @edit by yuxx at 2016-8-15,����10:16:33 ȡ����ļ����������������vbdef18,vbdef19,vbdef20��
				vbdef18 = util.getBodyUFDoubleValue(i, DelivBillBVO.VBDEF18);
				vbdef18 = vbdef18==null ? UFDouble.ZERO_DBL:vbdef18;
				vbdef19 = util.getBodyUFDoubleValue(i, DelivBillBVO.VBDEF19);
				vbdef19 = vbdef19==null ? UFDouble.ZERO_DBL:vbdef19;
				vbdef20 = util.getBodyUFDoubleValue(i, DelivBillBVO.VBDEF20);
				vbdef20 = vbdef20==null ? UFDouble.ZERO_DBL:vbdef20;
				
				ntotalastnum = ntotalastnum.add(nastnum);
				ntotalnum = ntotalnum.add(nnum);
				ntotalweight = ntotalweight.add(nweight);
				ntotalvolume = ntotalvolume.add(nvolume);
				ntotalmny = ntotalmny.add(nmny);
				
				// @edit by yuxx at 2016-8-15,����10:20:06 �ϼƼ��������������
				vdef15 = vdef15.add(vbdef18);
				vdef16 = vdef16.add(vbdef19);
				vdef17 = vdef17.add(vbdef20);
			}

			util.setHeadTailValue(DelivBillHVO.NTOTALASTNUM, ntotalastnum);
			util.setHeadTailValue(DelivBillHVO.NTOTALNUM, ntotalnum);
			util.setHeadTailValue(DelivBillHVO.NTOTALWEIGHT, ntotalweight);
			util.setHeadTailValue(DelivBillHVO.NTOTALVOLUME, ntotalvolume);
			util.setHeadTailValue(DelivBillHVO.NTOTALMNY, ntotalmny);
			// @edit by yuxx at 2016-8-6,����2:31:08
			util.setHeadTailValue(DelivBillHVO.VDEF15, vdef15);
			util.setHeadTailValue(DelivBillHVO.VDEF16, vdef16);
			util.setHeadTailValue(DelivBillHVO.VDEF17, vdef17);
			
			// @edit by yuxx at 2016-8-15,����10:27:16 ��ִ̨��ǰ̨vbillcode�ϵı༭��ʽ��
			//         �����ۿۣ�����ۿ۹�ʽ�仯һ����Ҫ��vbillcode�ı༭��ʽ�и��ģ�������Ч
			String[] formula=this.billCardPanel.getHeadItem("vbillcode").getEditFormulas();
			this.billCardPanel.execHeadFormulas(formula);
			//this.calDef();
		} else {
			util.setHeadTailValue(DelivBillHVO.NTOTALASTNUM, null);
			util.setHeadTailValue(DelivBillHVO.NTOTALNUM, null);
			util.setHeadTailValue(DelivBillHVO.NTOTALWEIGHT, null);
			util.setHeadTailValue(DelivBillHVO.NTOTALVOLUME, null);
			util.setHeadTailValue(DelivBillHVO.NTOTALMNY, null);
		}
	}

	private void linkagePack() {
		CardPanelValueUtils util = new CardPanelValueUtils(this.billCardPanel);
		// ���к�
		int rowCount = util.getRowCount();
		if (rowCount > 0) {
			// �ܰ�װ����
			UFDouble ntotalpacknum = UFDouble.ZERO_DBL;
			// �ܰ�װ����
			UFDouble ntotalpackweight = UFDouble.ZERO_DBL;
			// �ܰ�װ���
			UFDouble ntotalpackvolume = UFDouble.ZERO_DBL;

			// ��װ����
			UFDouble npacknum = UFDouble.ZERO_DBL;
			// ��װ����
			UFDouble npackweight = UFDouble.ZERO_DBL;
			// ��װ���
			UFDouble npackvolume = UFDouble.ZERO_DBL;
			for (int i = 0; i < rowCount; i++) {
				npacknum = util.getBodyUFDoubleValue(i,
						DelivBillPackVO.NPACKNUM);
				npacknum = npacknum == null ? UFDouble.ZERO_DBL : npacknum;
				npackweight = util.getBodyUFDoubleValue(i,
						DelivBillPackVO.NPACKWEIGHT);
				npackweight = npackweight == null ? UFDouble.ZERO_DBL
						: npackweight;
				npackvolume = util.getBodyUFDoubleValue(i,
						DelivBillPackVO.NPACKVOLUME);
				npackvolume = npackvolume == null ? UFDouble.ZERO_DBL
						: npackvolume;

				ntotalpacknum = ntotalpacknum.add(npacknum);
				ntotalpackweight = ntotalpackweight.add(npackweight);
				ntotalpackvolume = ntotalpackvolume.add(npackvolume);
			}

			util.setHeadTailValue(DelivBillHVO.NTOTALPACKNUM, ntotalpacknum);
			util.setHeadTailValue(DelivBillHVO.NTOTALPACKWEIGHT,
					ntotalpackweight);
			util.setHeadTailValue(DelivBillHVO.NTOTALPACKVOLUME,
					ntotalpackvolume);
			// @edit by yuxx at 2016-8-6,����2:31:08
			//this.calDef();
		} else {
			util.setHeadTailValue(DelivBillHVO.NTOTALPACKNUM, null);
			util.setHeadTailValue(DelivBillHVO.NTOTALPACKWEIGHT, null);
			util.setHeadTailValue(DelivBillHVO.NTOTALPACKVOLUME, null);
		}
	}
	/**
	 * �����ϼ�vdef15,16,17
	 * @create by yuxx at 2016-8-6,����2:30:18
	 *
	 */
/*	private void calDef() {
		CardPanelValueUtils util = new CardPanelValueUtils(this.billCardPanel);
		UFDouble totaldef18 = (UFDouble) this.billCardPanel.getTotalTableModel().getValueAt(
				0, 11);
		UFDouble totaldef19 = (UFDouble) this.billCardPanel.getTotalTableModel().getValueAt(
				0, 12);
		UFDouble totaldef20 = (UFDouble) this.billCardPanel.getTotalTableModel().getValueAt(
				0, 13);
		util.setHeadTailValue(DelivBillHVO.VDEF15, totaldef18);// ����
		util.setHeadTailValue(DelivBillHVO.VDEF16, totaldef19);// ����
		util.setHeadTailValue(DelivBillHVO.VDEF17, totaldef20);// ���
		*//**
		 * ��ִ̨��ǰ̨vbillcode�ϵı༭��ʽ�������ۿۣ�����ۿ۹�ʽ�仯һ����Ҫ��vbillcode�ı༭��ʽ�и��ģ�������Ч
		 *//*
		String[] formula=this.billCardPanel.getHeadItem("vbillcode").getEditFormulas();
		this.billCardPanel.execHeadFormulas(formula);
	}*/
}
