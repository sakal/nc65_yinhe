/**
 * 
 */
package nc.ui.dm.m4804.handler;

import nc.ui.dm.m4804.editor.util.M4804LinkageUtil;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent;
import nc.ui.pubapp.uif2app.event.card.CardBodyBeforeEditEvent;
import nc.ui.pubapp.util.CardPanelValueUtils;
import nc.vo.dm.m4804.entity.DelivBillBVO;
import nc.vo.dm.m4804.entity.DelivBillHVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.data.ValueUtils;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.pub.PubAppTool;

/**
 * @author yuxx
 * @created at 2016-8-5,����5:03:18
 *
 */
public class NumberEditHandler {

	  /**
	   * �������༭���¼�
	   * 
	   * @param e
	   */
	  public void afterEdit(CardBodyAfterEditEvent e) {
	    BillCardPanel panel = e.getBillCardPanel();
	    CardPanelValueUtils util = new CardPanelValueUtils(panel);
	    UFDouble nnum = util.getBodyUFDoubleValue(e.getRow(), DelivBillBVO.NNUM);
	    if (nnum == null) {
	      util.setBodyValue(null, e.getRow(), DelivBillBVO.NMONEY);
	      util.setBodyValue(null, e.getRow(), DelivBillBVO.NASTNUM);
	    }
	    else if (nnum.compareTo(UFDouble.ZERO_DBL) <= 0) {
	      util.setBodyValue(null, e.getRow(), DelivBillBVO.NNUM);
	      ExceptionUtils.wrappBusinessException(NCLangRes.getInstance().getStrByID(
	          "4014001_0", "04014001-0072")/*�������������0*/);
	    }
	    else {
	      // ���á��������۽����㷨��
	      this.setNPM(e);
	    }
	    // ������ͷ�ϼ��ֶ�
	    M4804LinkageUtil linkageutil = new M4804LinkageUtil(panel, 0);

	    linkageutil.linkage();
	  }

	  /**
	   * �������༭ǰ�¼�
	   * 
	   * @param e
	   */
	  public void beforeEdit(CardBodyBeforeEditEvent e) {
	    BillCardPanel panel = e.getBillCardPanel();
	    CardPanelValueUtils util = new CardPanelValueUtils(panel);
	    // ����Ϊ�գ����ɱ༭
	    String inventory =
	        ValueUtils.getString(util.getBodyValue(e.getRow(),
	            DelivBillBVO.CINVENTORYVID, null));
	    if (PubAppTool.isNull(inventory)) {
	      MessageDialog.showHintDlg(
	          e.getBillCardPanel(),
	          null,
	          nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("4014001_0",
	              "04014001-0225")/*@res "����ѡ�����ϡ�"*/);
	      e.setReturnValue(Boolean.valueOf(false));
	      return;
	    }
	  }

	  /**
	   * ���ݵ�λ(����λ)�������������ۡ����
	   * 
	   * @param e
	   */
	  private void setNPM(CardBodyAfterEditEvent e) {
	    BillCardPanel panel = e.getBillCardPanel();
	    NumPriceMnyUtil editUtil = new NumPriceMnyUtil(panel);
	    editUtil.calculateNumPriceMny(e.getRow(), e.getKey());
	  }
	}
