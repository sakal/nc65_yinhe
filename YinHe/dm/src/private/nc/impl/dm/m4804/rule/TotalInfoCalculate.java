package nc.impl.dm.m4804.rule;

import nc.impl.pubapp.pattern.rule.IRule;
import nc.vo.dm.m4804.entity.DelivBillAggVO;
import nc.vo.dm.m4804.entity.DelivBillBVO;
import nc.vo.dm.m4804.entity.DelivBillHVO;
import nc.vo.dm.m4804.entity.DelivBillPackVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.pub.MathTool;

/**
 * ���ݱ������ϵļ�¼�ϼƵ���ͷ������ֶ���
 * 
 * @since 6.0
 * @version 2010-11-19 ����02:39:14
 * @author yinyxa
 */
public class TotalInfoCalculate implements IRule<DelivBillAggVO> {

  private UFDouble zero = new UFDouble(0);

  @Override
  public void process(DelivBillAggVO[] vos) {
    for (DelivBillAggVO bill : vos) {
      this.calculateBillBody(bill);
      this.calculatePackBody(bill);
    }
  }

  private void calculateBillBody(DelivBillAggVO bill) {
    DelivBillBVO[] vos = bill.getDelivBillBVO();
    if (vos == null) {
      return;
    }

    UFDouble ntotalnum = this.zero;
    UFDouble ntotalastnum = this.zero;
    UFDouble ntotalmny = this.zero;
    UFDouble ntotalweight = this.zero;
    UFDouble ntotalvolumn = this.zero;
    UFDouble ntotalsignnum = this.zero;
    UFDouble ntotalsignastnum = this.zero;
    UFDouble ntotalsignweight = this.zero;
    UFDouble ntotalsignvolume = this.zero;

    for (DelivBillBVO vo : vos) {
      if (vo.getStatus() == VOStatus.DELETED) {
        continue;
      }
      UFDouble nnumber = vo.getNnum();
      ntotalnum = MathTool.add(ntotalnum, nnumber);

      UFDouble nastnum = vo.getNastnum();
      ntotalastnum = MathTool.add(ntotalastnum, nastnum);

      UFDouble nmoney = vo.getNmoney();
      ntotalmny = MathTool.add(ntotalmny, nmoney);

      UFDouble nweight = vo.getNweight();
      ntotalweight = MathTool.add(ntotalweight, nweight);

      UFDouble nvolumn = vo.getNvolumn();
      ntotalvolumn = MathTool.add(ntotalvolumn, nvolumn);

      UFDouble nsignnum = vo.getNsignnum();
      ntotalsignnum = MathTool.add(ntotalsignnum, nsignnum);

      UFDouble nsignastnum = vo.getNsignastnum();
      ntotalsignastnum = MathTool.add(ntotalsignastnum, nsignastnum);

      UFDouble nsignweight = vo.getNsignweight();
      ntotalsignweight = MathTool.add(ntotalsignweight, nsignweight);

      UFDouble nsignvolume = vo.getNsignvolume();
      ntotalsignvolume = MathTool.add(ntotalsignvolume, nsignvolume);
    }

    DelivBillHVO head = bill.getParentVO();
    if (head.getStatus() == VOStatus.UNCHANGED) {
      head.setStatus(VOStatus.UPDATED);
    }
    // ��������
    head.setNtotalnum(ntotalnum);
    // ������
    head.setNtotalastnum(ntotalastnum);
    // �ܽ��
    head.setNtotalmny(ntotalmny);
    // ������
    head.setNtotalweight(ntotalweight);
    // �����
    head.setNtotalvolume(ntotalvolumn);
    // ��ǩ��������
    head.setNtotalsignnum(ntotalsignnum);
    // ��ǩ������
    head.setNtotalsignastnum(ntotalsignastnum);
    // ��ǩ������
    head.setNtotalsignweight(ntotalsignweight);
    // ��ǩ�����
    head.setNtotalsignvolume(ntotalsignvolume);

  }

  private void calculatePackBody(DelivBillAggVO bill) {
    DelivBillPackVO[] vos = bill.getDelivBillPackVO();
    if (vos == null) {
      return;
    }

    UFDouble ntotalpacknum = this.zero;
    UFDouble ntotalpackweight = this.zero;
    UFDouble ntotalpackvolume = this.zero;
    UFDouble ntotalsignpacknum = this.zero;
    UFDouble ntotalsignpackweight = this.zero;
    UFDouble ntotalsignpackvolumn = this.zero;

    for (DelivBillPackVO vo : vos) {
      if (vo.getStatus() == VOStatus.DELETED) {
        continue;
      }

      UFDouble npacknum = vo.getNpacknum();
      ntotalpacknum = MathTool.add(ntotalpacknum, npacknum);

      UFDouble npackweight = vo.getNpackweight();
      ntotalpackweight = MathTool.add(ntotalpackweight, npackweight);

      UFDouble npackvolume = vo.getNpackvolume();
      ntotalpackvolume = MathTool.add(ntotalpackvolume, npackvolume);

      UFDouble nsignpacknum = vo.getNsignpacknum();
      ntotalsignpacknum = MathTool.add(ntotalsignpacknum, nsignpacknum);

      UFDouble nsignpackvolumn = vo.getNsignpackvolumn();
      ntotalsignpackvolumn =
          MathTool.add(ntotalsignpackvolumn, nsignpackvolumn);

      UFDouble nsignpackweight = vo.getNsignpackweight();
      ntotalsignpackweight =
          MathTool.add(ntotalsignpackweight, nsignpackweight);
    }

    DelivBillHVO head = bill.getParentVO();
    if (head.getStatus() == VOStatus.UNCHANGED) {
      head.setStatus(VOStatus.UPDATED);
    }

    // �ܰ�װ����
    head.setNtotalpacknum(ntotalpacknum);
    // �ܰ�װ����
    head.setNtotalpackweight(ntotalpackweight);
    // �ܰ�װ���
    head.setNtotalpackvolume(ntotalpackvolume);
    // ��ǩ�հ�װ����
    head.setNtotalsignpknum(ntotalsignpacknum);
    // ��ǩ�հ�װ����
    head.setNtotalsignpkweight(ntotalsignpackweight);
    // ��ǩ�հ�װ���
    head.setNtotalsignpkvolume(ntotalsignpackvolumn);
  }

}
