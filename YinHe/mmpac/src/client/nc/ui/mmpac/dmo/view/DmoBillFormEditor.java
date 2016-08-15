/**
 * 
 */
package nc.ui.mmpac.dmo.view;

import java.util.List;

import javax.swing.Action;
import nc.ui.mm.ref.pbref.PbRefPane;
import nc.ui.mmf.framework.view.BillFormFacade;
import nc.ui.mmpac.dmo.model.DmoBillManageModel;
import nc.ui.mmpac.dmo.scale.ProcedureScleTimeutil;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.itemeditors.UFRefBillItemEditor;
import nc.ui.pubapp.uif2app.actions.AbstractBodyTableExtendAction;
import nc.ui.pubapp.uif2app.funcnode.trantype.TrantypeFuncUtils;
import nc.util.mmf.framework.base.MMNumberUtil;
import nc.util.mmf.framework.base.MMStringUtil;
import nc.util.mmf.framework.base.MMValueCheck;
import nc.vo.mmpac.dmo.entity.AggDmoVO;
import nc.vo.mmpac.dmo.entity.DmoVO;
import nc.vo.mmpac.dmo.enumeration.DmoStatusEnum;
import nc.vo.pub.lang.UFDouble;

/**
 * @author yuxx
 * @created at 2016-8-8,����7:33:17
 *
 */
public class DmoBillFormEditor extends BillFormFacade {

    @Override
    public void initUI() {
        super.initUI();
        this.scaleProcess();
        
    }

    @Override
    protected void onAdd() {

        super.onAdd();
        this.setBillItemPropertyForAdd();
        // �������һЩǰ̨���Ʋ����޸���Ӱ����������Ҫ���������´򿪣��緵������
        this.setBillItemPropertyForBYPUSHCLOSE();
        // �����˽������ͽڵ�Ķ������Ͳ�����༭
        String trantype = TrantypeFuncUtils.getTrantype(this.getModel().getContext());
        if (MMStringUtil.isNotEmpty(trantype)) {
            // ƽ̨���Ʋ��˲��ɱ༭�ԣ����ڴ����ӣ�
            this.getHeadItem(DmoVO.VTRANTYPEID).setEdit(false);
            this.getHeadItem(DmoVO.VTRANTYPECODE).setEdit(false);
            return;
        }
    }

    @Override
    public void setValue(Object object) {
        super.setValue(object);
        AggDmoVO aggVO = (AggDmoVO) object;
        if (aggVO == null) {
            return;
        }
        DmoVO vo = aggVO.getParentVO();
        String batchCode = vo.getVbatchcode();
        BillItem billItem = this.getHeadItem(DmoVO.VBATCHCODE);
        UIRefPane refPane = (UIRefPane) billItem.getComponent();
        refPane.getUITextField().setText(batchCode);
    }

    @Override
    protected void onEdit() {

        super.onEdit();
        this.setBillItemPropertyForEdit();
        // this.setEditEnableForEdit();
    }

    /***
     * �޸�ʱͶ��̬�����޸ĵ���Ŀ��<br>
     * Ԥ���깤���ڣ���ע
     */
    public static final String[] EDITABLEITEMKEYS_PUTSTATE = new String[] {
        /* DmoVO.VBATCHCODE, */DmoVO.TWILLENDTIME, DmoVO.VNOTE, DmoVO.VPRODBATDEF1, DmoVO.VPRODBATDEF2,
        DmoVO.VPRODBATDEF3, DmoVO.VPRODBATDEF4, DmoVO.VPRODBATDEF5, DmoVO.VPRODBATDEF6, DmoVO.VPRODBATDEF7,
        DmoVO.VPRODBATDEF8, DmoVO.VPRODBATDEF9, DmoVO.VPRODBATDEF10, DmoVO.VPRODBATDEF11, DmoVO.VPRODBATDEF12,
        DmoVO.VPRODBATDEF13, DmoVO.VPRODBATDEF14, DmoVO.VPRODBATDEF15, DmoVO.VPRODBATDEF16, DmoVO.VPRODBATDEF17,
        DmoVO.VPRODBATDEF18, DmoVO.VPRODBATDEF19, DmoVO.VPRODBATDEF20, DmoVO.VPRODBATNOTE,DmoVO.VDEF2 // @edit by yuxx at 2016-8-8,����7:34:07 ��Ӷ������˵��
    };

    /***
     * ���ʱ Ͷ��̬�����޸ĵ���Ŀ��<br>
     * Ԥ���깤���ڣ���ע
     */
    public static final String[] REVISEABLEITEMKEYS_PUTSTATE = new String[] {
        DmoVO.TPLANENDTIME, DmoVO.NMMASTNUM, DmoVO.NPLANPUTASTNUM, DmoVO.NMMNUM, DmoVO.NPLANPUTNUM, DmoVO.TWILLENDTIME,
        DmoVO.VNOTE,DmoVO.VDEF2 // @edit by yuxx at 2016-8-8,����7:34:07 ��Ӷ������˵��
    };

    /***
     * �������һЩǰ̨���Ʋ����޸���Ӱ����������Ҫ���������´򿪣��緵������
     */
    public static final String[] ADDOPENBLEITEMKEYSBYPUSHCLOSE = new String[] {
        DmoVO.VTRANTYPEID, DmoVO.CMATERIALVID, DmoVO.NMMNUM, DmoVO.NMMASTNUM
    };

    /***
     * �޸����̬�����޸ĵ���Ŀ��<br>
     * Ԥ���깤���ڣ���ע
     */
    public static final String[] EDITABLEITEMKEYS_AUDITSTATE = new String[] {
        DmoVO.NMMASTNUM, DmoVO.NPLANPUTASTNUM, DmoVO.NMMNUM, DmoVO.NPLANPUTNUM, DmoVO.TPLANENDTIME,
        DmoVO.TPLANSTARTTIME, DmoVO.VBATCHCODE, DmoVO.TWILLENDTIME, DmoVO.VNOTE, DmoVO.VPRODBATDEF1,
        DmoVO.VPRODBATDEF2, DmoVO.VPRODBATDEF3, DmoVO.VPRODBATDEF4, DmoVO.VPRODBATDEF5, DmoVO.VPRODBATDEF6,
        DmoVO.VPRODBATDEF7, DmoVO.VPRODBATDEF8, DmoVO.VPRODBATDEF9, DmoVO.VPRODBATDEF10, DmoVO.VPRODBATDEF11,
        DmoVO.VPRODBATDEF12, DmoVO.VPRODBATDEF13, DmoVO.VPRODBATDEF14, DmoVO.VPRODBATDEF15, DmoVO.VPRODBATDEF16,
        DmoVO.VPRODBATDEF17, DmoVO.VPRODBATDEF18, DmoVO.VPRODBATDEF19, DmoVO.VPRODBATDEF20, DmoVO.VPRODBATNOTE,
        DmoVO.VDEF2 // @edit by yuxx at 2016-8-8,����7:34:07 ��Ӷ������˵��
    };

    /***
     * ������̬�����޸ĵ���Ŀ��<br>
     * Ԥ���깤���ڣ���ע
     */
    public static final String[] REVISEABLEITEMKEYS_AUDITSTATE = new String[] {
        DmoVO.TPLANSTARTTIME, DmoVO.TPLANENDTIME, DmoVO.NMMASTNUM, DmoVO.NPLANPUTASTNUM, DmoVO.NMMNUM,
        DmoVO.NPLANPUTNUM, DmoVO.VBATCHCODE, DmoVO.TWILLENDTIME, DmoVO.VNOTE, DmoVO.VPRODBATDEF1, DmoVO.VPRODBATDEF2,
        DmoVO.VPRODBATDEF3, DmoVO.VPRODBATDEF4, DmoVO.VPRODBATDEF5, DmoVO.VPRODBATDEF6, DmoVO.VPRODBATDEF7,
        DmoVO.VPRODBATDEF8, DmoVO.VPRODBATDEF9, DmoVO.VPRODBATDEF10, DmoVO.VPRODBATDEF11, DmoVO.VPRODBATDEF12,
        DmoVO.VPRODBATDEF13, DmoVO.VPRODBATDEF14, DmoVO.VPRODBATDEF15, DmoVO.VPRODBATDEF16, DmoVO.VPRODBATDEF17,
        DmoVO.VPRODBATDEF18, DmoVO.VPRODBATDEF19, DmoVO.VPRODBATDEF20, DmoVO.VPRODBATNOTE,
        DmoVO.VDEF2 // @edit by yuxx at 2016-8-8,����7:34:07 ��Ӷ������˵��
    };

    /***
     * ����ʱ�������޸ĵ���Ŀ��<br>
     */
    public static final String[] EDITABLEITEMKEYS_ADD = new String[] {
        DmoVO.NNUM, DmoVO.NASTNUM, DmoVO.CMATERIALID, DmoVO.VPACKBOMVER, DmoVO.VBOMVERSION, DmoVO.VRTVERSION,
        DmoVO.TACTENDTIME, DmoVO.FBILLSTATUS, DmoVO.TACTSTARTTIME, DmoVO.NWRASTNUM, DmoVO.NWRNUM, DmoVO.NINASTNUM,
        DmoVO.NINNUM, DmoVO.NREWORKDMOASTNUM, DmoVO.NREWORKDMONUM, DmoVO.NDISCARDASTNUM, DmoVO.NDISCARDDMOASTNUM,
        DmoVO.NDISCARDDMONUM, DmoVO.NDISCARDNUM, DmoVO.NZCGASTNUM, DmoVO.NZCGNUM, DmoVO.NZDBASTNUM, DmoVO.NZDBNUM,
        DmoVO.NZWWASTNUM, DmoVO.NZWWNUM, DmoVO.VFIRSTBID, DmoVO.VFIRSTCODE, DmoVO.VFIRSTID, DmoVO.VFIRSTMOCODE,
        DmoVO.VFIRSTMOID, DmoVO.VFIRSTROWNO, DmoVO.VFIRSTTRANTYPECODE, DmoVO.VFIRSTTRANTYPEID, DmoVO.VFIRSTTYPE,
        DmoVO.VSRCBID, DmoVO.VSRCCODE, DmoVO.VSRCID, DmoVO.VSRCROWNO, DmoVO.VSRCTRANTYPECODE, DmoVO.VSRCTRANTYPEID,
        DmoVO.VSRCTYPE, DmoVO.VORIGMOCODE, DmoVO.VORIGMOID, DmoVO.VPARENTMOBID, DmoVO.VPARENTMOID, DmoVO.VPARENTMOCODE,
        DmoVO.VPARENTMOROWNO, DmoVO.VPARENTMOTYPE, DmoVO.VBILLTYPE, DmoVO.DDEMANDTIME, DmoVO.DPLANSUPPLYTIME,
        DmoVO.PK_PLANORG, DmoVO.PK_DEMANDORG, DmoVO.PK_DEMANDORG_V, DmoVO.VERSION, DmoVO.CECNID,
        DmoVO.VDEF2 // @edit by yuxx at 2016-8-8,����7:34:07 ��Ӷ������˵��
    };

    /***
     * �ƻ�̬�����޸ĵ���Ŀ:<br>
     * ҵ��Ա���������ţ����۶����ţ��������ͣ�����λ���ƻ��������ڣ��ƻ���ʼʱ�䣬�ƻ��깤���ڣ�<br>
     * �ƻ�����ʱ�䣬�������ƻ�Ͷ��������Ԥ���깤���ڣ������κţ��Ƿ񵹳�[PDM��û�и��ֶΰ�����2010-1-29 10:00:25]��<br>
     * �Ƿ�Ӽ���BOM�汾������·�߰汾���ƻ�����<br>
     * ���ȼ����ͻ������ϣ���ע��������
     */
    public static final String[] EDITABLEITEMKEYS_PLANSATE = new String[] {
        DmoVO.NMMNUM, DmoVO.VCHANGERATE, DmoVO.CPLANNERID, DmoVO.CDEPTVID, DmoVO.CASTUNITID, DmoVO.TPLANSTARTTIME,
        DmoVO.TPLANENDTIME, DmoVO.NMMASTNUM, DmoVO.TWILLENDTIME, DmoVO.VBATCHCODE, DmoVO.NPLANPUTASTNUM,
        DmoVO.NPLANPUTNUM, DmoVO.CBOMVERSIONID, DmoVO.CRTVERSIONID, DmoVO.CCUSTOMERID, DmoVO.CFFILEID,
        DmoVO.NSCRAPFACTOR, DmoVO.CPACKBOMVID, DmoVO.CPDMOPROCEDURENO,
        /* DmoVO.VDOCADDRESS, */DmoVO.VNOTE, DmoVO.VFREE1, DmoVO.VFREE2, DmoVO.VFREE3, DmoVO.VFREE4, DmoVO.VFREE5,
        DmoVO.VFREE6, DmoVO.VFREE7, DmoVO.VFREE8, DmoVO.VFREE9, DmoVO.VFREE10,
        /* DmoVO.CPLANFACTORYVID, */DmoVO.CPROJECTID, DmoVO.CPRODUCTORID, DmoVO.CVENDORID, DmoVO.VDEF1, DmoVO.VDEF2,
        DmoVO.VDEF3, DmoVO.VDEF4, DmoVO.VDEF5, DmoVO.VDEF6, DmoVO.VDEF7, DmoVO.VDEF8, DmoVO.VDEF9, DmoVO.VDEF10,
        DmoVO.VDEF11, DmoVO.VDEF12, DmoVO.VDEF13, DmoVO.VDEF14, DmoVO.VDEF15, DmoVO.VDEF16, DmoVO.VDEF17, DmoVO.VDEF18,
        DmoVO.VDEF19, DmoVO.VDEF20, DmoVO.BURGENT, DmoVO.VPRODBATDEF1, DmoVO.VPRODBATDEF2, DmoVO.VPRODBATDEF3,
        DmoVO.VPRODBATDEF4, DmoVO.VPRODBATDEF5, DmoVO.VPRODBATDEF6, DmoVO.VPRODBATDEF7, DmoVO.VPRODBATDEF8,
        DmoVO.VPRODBATDEF9, DmoVO.VPRODBATDEF10, DmoVO.VPRODBATDEF11, DmoVO.VPRODBATDEF12, DmoVO.VPRODBATDEF13,
        DmoVO.VPRODBATDEF14, DmoVO.VPRODBATDEF15, DmoVO.VPRODBATDEF16, DmoVO.VPRODBATDEF17, DmoVO.VPRODBATDEF18,
        DmoVO.VPRODBATDEF19, DmoVO.VPRODBATDEF20, DmoVO.VPRODBATNOTE, DmoVO.CFFILEID,
        DmoVO.VDEF2 // @edit by yuxx at 2016-8-8,����7:34:07 ��Ӷ������˵��
    };

    /***
     * �ƻ�̬���ܷ���������޸ĵ���Ŀ:<br>
     * ҵ��Ա���������ţ��������ģ����۶����ţ��������ͣ��ƻ��������ڣ��ƻ���ʼʱ�䣬�ƻ��깤���ڣ�<br>
     * �ƻ�����ʱ�䣬�������ƻ�Ͷ��������Ԥ���깤���ڣ���Σ����飬���κţ��Ƿ񵹳�[PDM��û�и��ֶΰ�����2010-1-29 10:00:25]��<br>
     * �Ƿ�Ӽ����ƻ�����<br>
     * ���ȼ����ͻ������ϣ���ע��������
     */
    public static final String[] EDITABLEITEMKEYS_PLANSATEANDTURN = new String[] {
        DmoVO.TPLANSTARTTIME, DmoVO.TPLANENDTIME, DmoVO.NMMASTNUM, DmoVO.TWILLENDTIME, DmoVO.VBATCHCODE,
        DmoVO.NPLANPUTASTNUM, DmoVO.NMMNUM, DmoVO.NPLANPUTASTNUM, DmoVO.NPLANPUTNUM, DmoVO.CCUSTOMERID, DmoVO.CDEPTVID,
        DmoVO.BURGENT, DmoVO.VPRODBATDEF1, DmoVO.VPRODBATDEF2, DmoVO.VPRODBATDEF3, DmoVO.VPRODBATDEF4,
        DmoVO.VPRODBATDEF5, DmoVO.VPRODBATDEF6, DmoVO.VPRODBATDEF7, DmoVO.VPRODBATDEF8, DmoVO.VPRODBATDEF9,
        DmoVO.VPRODBATDEF10, DmoVO.VPRODBATDEF11, DmoVO.VPRODBATDEF12, DmoVO.VPRODBATDEF13, DmoVO.VPRODBATDEF14,
        DmoVO.VPRODBATDEF15, DmoVO.VPRODBATDEF16, DmoVO.VPRODBATDEF17, DmoVO.VPRODBATDEF18, DmoVO.VPRODBATDEF19,
        DmoVO.VPRODBATDEF20, DmoVO.VPRODBATNOTE, 
        DmoVO.VNOTE,DmoVO.VDEF2 // @edit by yuxx at 2016-8-8,����7:34:07 ��Ӷ������˵��
    };

    /***
     * ����������������ʱ��BillItem��һЩ���Բ��ɱ༭����
     */
    private void setBillItemPropertyForAdd() {

        if (!MMValueCheck.isEmpty(this.billCardPanel.getHeadItem(DmoVO.PK_ORG).getValueObject())) {
            for (String itemKey : DmoBillFormEditor.EDITABLEITEMKEYS_ADD) {
                this.getBillCardPanel().getHeadItem(itemKey).setEdit(false);
            }
            this.getBillCardPanel().getHeadItem(DmoVO.CMATERIALVID).setEdit(true);
          
        }
    }

    /***
     * �������һЩǰ̨���Ʋ����޸���Ӱ����������Ҫ���������´򿪣��緵������
     */
    private void setBillItemPropertyForBYPUSHCLOSE() {

        if (!MMValueCheck.isEmpty(this.billCardPanel.getHeadItem(DmoVO.PK_ORG).getValueObject())) {
            for (String itemKey : DmoBillFormEditor.ADDOPENBLEITEMKEYSBYPUSHCLOSE) {
                this.getBillCardPanel().getHeadItem(itemKey).setEdit(true);
            }
        }
    }

    /***
     * ����������������ʱ��BillItem��һЩ���Բ��ɱ༭����
     */
    private void setBillItemPropertyForEdit() {

        DmoVO vo = this.getSelectedDmoVO();
        if (DmoStatusEnum.FREE.equalsValue(vo.getFbillstatus())) {
            this.setBillItemPropertyForPlanState(true, vo);
        }
        else if (DmoStatusEnum.AUDIT.equalsValue(vo.getFbillstatus())) {
            this.setBillItemPropertyForAuditState(vo);
            // this.bodyActionEnableControl();
        }
        else if (DmoStatusEnum.PUT.equalsValue(vo.getFbillstatus())) {
            this.setBillItemPropertyForPlanState(false, vo);
            // this.bodyActionEnableControl();
        }
    }

    @Override
    protected void processBillData(nc.ui.pub.bill.BillData data) {
        /*
         * BillItem hslItem = data.getHeadItem(DmoVO.VCHANGERATE);
         * MetaDataEditPropertyAdpter metaDataProperty = new MetaDataEditPropertyAdpter(hslItem.getMetaDataProperty());
         * metaDataProperty.setDatatype(IBillItem.FRACTION);
         * hslItem.setMetaDataProperty(metaDataProperty);
         */
        // ����ģ�����������κŲ���
        PbRefPane refpane = new PbRefPane();
        BillItem billItem = data.getHeadItem(DmoVO.VBATCHCODE);
        UFRefBillItemEditor editor = new UFRefBillItemEditor(billItem);
        editor.setComponent(refpane);
        refpane.setAutoCheck(false);
        refpane.setAutoscrolls(false);
        refpane.revalidate();
        refpane.setFormat(false);
        billItem.setComponent(refpane);
        billItem.setItemEditor(editor);
    }

    /**
     * ���尴ť���Ʊ༭��
     */
    private void bodyActionEnableControl() {
        List<Action> actionList = this.getBodyLineActions();
        if (MMValueCheck.isEmpty(actionList)) {
            return;
        }
        if (actionList.size() == 10) {
            actionList.remove(8);// �Ƴ��ո�ť
            actionList.remove(6);// �Ƴ��ո�ť
        }
        AbstractBodyTableExtendAction[] actions = actionList.toArray(new AbstractBodyTableExtendAction[0]);
        for (AbstractBodyTableExtendAction action : actions) {
            action.setEnabled(false);
        }
    }

    /**
     * @param vo
     */
    private void setBillItemPropertyForAuditState(DmoVO vo) {
        this.billCardPanel.setEnabled(false);
        DmoBillManageModel model = (DmoBillManageModel) this.getModel();
        if (model.isAdjust()) {
            for (String itemKey : DmoBillFormEditor.REVISEABLEITEMKEYS_AUDITSTATE) {

                this.billCardPanel.getHeadItem(itemKey).setEnabled(true);
            }
        }
        else {
            for (String itemKey : DmoBillFormEditor.EDITABLEITEMKEYS_AUDITSTATE) {

                this.billCardPanel.getHeadItem(itemKey).setEnabled(true);
            }
        }
        this.getBillCardPanel().getBillModel("pk_procedure").setEnabled(true);
        // this.billCardPanel.getBodyPanel().setEnabled(isEditable());
    }

    /***
     * ���üƻ�̬��Ͷ��̬���������޸�ʱ��BillItem��һЩ����
     * 
     * @param isPlan
     *            :boolean
     */
    private void setBillItemPropertyForPlanState(boolean isPlan, DmoVO vo) {
        if (isPlan) {
            this.billCardPanel.setEnabled(false);
            UFDouble divNum = MMNumberUtil.add(vo.getNzwwnum(), vo.getNzcgnum());
            if (MMNumberUtil.isGtZero(divNum)) {
                for (String itemKey : DmoBillFormEditor.EDITABLEITEMKEYS_PLANSATEANDTURN) {
                    this.billCardPanel.getHeadItem(itemKey).setEnabled(true);
                }
            }
            else {
                for (String itemKey : DmoBillFormEditor.EDITABLEITEMKEYS_PLANSATE) {
                    this.billCardPanel.getHeadItem(itemKey).setEnabled(true);

                }
            }
            this.getBillCardPanel().getBillModel("pk_procedure").setEnabled(true);
        }
        else {
            this.billCardPanel.setEnabled(false);

            DmoBillManageModel model = (DmoBillManageModel) this.getModel();
            if (model.isAdjust()) {
                for (String itemKey : DmoBillFormEditor.REVISEABLEITEMKEYS_PUTSTATE) {

                    this.billCardPanel.getHeadItem(itemKey).setEnabled(true);
                }
            }
            else {
                for (String itemKey : DmoBillFormEditor.EDITABLEITEMKEYS_PUTSTATE) {

                    this.billCardPanel.getHeadItem(itemKey).setEnabled(true);
                }
            }
            this.getBillCardPanel().getBillModel("pk_procedure").setEnabled(true);
        }
    }

    private DmoVO getSelectedDmoVO() {
        Object obj = this.getModel().getSelectedData();
        DmoVO vo = null;
        if (obj instanceof AggDmoVO) {
            AggDmoVO aggvo = (AggDmoVO) obj;
            vo = aggvo.getParentVO();
        }
        else {
            vo = (DmoVO) obj;
        }
        return vo;

    }

    /**
     * ���ȴ���
     */
    private void scaleProcess() {
        new ProcedureScleTimeutil().setCardScale(this);
    }
}

