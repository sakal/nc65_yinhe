package nc.vo.uapbd.orderarearela;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

/**
 * <b> �˴���Ҫ�������๦�� </b>
 * <p>
 *   �˴�����۵�������Ϣ
 * </p>
 *  ��������:2016-5-27
 * @author YONYOU NC
 * @version NCPrj ??
 */
 
public class Orderrelation extends SuperVO {
	
/**
*����
*/
public String pk_rela;
/**
*����
*/
public String pk_group;
/**
*��֯
*/
public String pk_org;
/**
*��綩���ֶ�
*/
public String gdcode;
/**
*�Զ�����1
*/
public String vdef1;
/**
*�Զ�����2
*/
public String vdef2;
/**
*�Զ�����3
*/
public String vdef3;
/**
*�Զ�����4
*/
public String vdef4;
/**
*�Զ�����5
*/
public String vdef5;
/**
*�Զ�����6
*/
public String vdef6;
/**
*�Զ�����7
*/
public String vdef7;
/**
*�Զ�����8
*/
public String vdef8;
/**
*�Զ�����9
*/
public String vdef9;
/**
*�Զ�����10
*/
public String vdef10;
/**
*������
*/
public String creator;
/**
*����ʱ��
*/
public UFDateTime creationtime;
/**
*����޸���
*/
public String modifier;
/**
*����޸�ʱ��
*/
public UFDateTime modifiedtime;
/**
*ʱ���
*/
public UFDateTime ts;
    
    
/**
* ���� pk_rela��Getter����.������������
*  ��������:2016-5-27
* @return java.lang.String
*/
public String getPk_rela() {
return this.pk_rela;
} 

/**
* ����pk_rela��Setter����.������������
* ��������:2016-5-27
* @param newPk_rela java.lang.String
*/
public void setPk_rela ( String pk_rela) {
this.pk_rela=pk_rela;
} 
 
/**
* ���� pk_group��Getter����.������������
*  ��������:2016-5-27
* @return nc.vo.org.GroupVO
*/
public String getPk_group() {
return this.pk_group;
} 

/**
* ����pk_group��Setter����.������������
* ��������:2016-5-27
* @param newPk_group nc.vo.org.GroupVO
*/
public void setPk_group ( String pk_group) {
this.pk_group=pk_group;
} 
 
/**
* ���� pk_org��Getter����.����������֯
*  ��������:2016-5-27
* @return nc.vo.org.OrgVO
*/
public String getPk_org() {
return this.pk_org;
} 

/**
* ����pk_org��Setter����.����������֯
* ��������:2016-5-27
* @param newPk_org nc.vo.org.OrgVO
*/
public void setPk_org ( String pk_org) {
this.pk_org=pk_org;
} 
 
/**
* ���� gdcode��Getter����.����������綩���ֶ�
*  ��������:2016-5-27
* @return nc.vo.uapbd.gdorder.Enumerate0
*/
public String getGdcode() {
return this.gdcode;
} 

/**
* ����gdcode��Setter����.����������綩���ֶ�
* ��������:2016-5-27
* @param newGdcode nc.vo.uapbd.gdorder.Enumerate0
*/
public void setGdcode ( String gdcode) {
this.gdcode=gdcode;
} 
 
/**
* ���� vdef1��Getter����.���������Զ�����1
*  ��������:2016-5-27
* @return java.lang.String
*/
public String getVdef1() {
return this.vdef1;
} 

/**
* ����vdef1��Setter����.���������Զ�����1
* ��������:2016-5-27
* @param newVdef1 java.lang.String
*/
public void setVdef1 ( String vdef1) {
this.vdef1=vdef1;
} 
 
/**
* ���� vdef2��Getter����.���������Զ�����2
*  ��������:2016-5-27
* @return java.lang.String
*/
public String getVdef2() {
return this.vdef2;
} 

/**
* ����vdef2��Setter����.���������Զ�����2
* ��������:2016-5-27
* @param newVdef2 java.lang.String
*/
public void setVdef2 ( String vdef2) {
this.vdef2=vdef2;
} 
 
/**
* ���� vdef3��Getter����.���������Զ�����3
*  ��������:2016-5-27
* @return java.lang.String
*/
public String getVdef3() {
return this.vdef3;
} 

/**
* ����vdef3��Setter����.���������Զ�����3
* ��������:2016-5-27
* @param newVdef3 java.lang.String
*/
public void setVdef3 ( String vdef3) {
this.vdef3=vdef3;
} 
 
/**
* ���� vdef4��Getter����.���������Զ�����4
*  ��������:2016-5-27
* @return java.lang.String
*/
public String getVdef4() {
return this.vdef4;
} 

/**
* ����vdef4��Setter����.���������Զ�����4
* ��������:2016-5-27
* @param newVdef4 java.lang.String
*/
public void setVdef4 ( String vdef4) {
this.vdef4=vdef4;
} 
 
/**
* ���� vdef5��Getter����.���������Զ�����5
*  ��������:2016-5-27
* @return java.lang.String
*/
public String getVdef5() {
return this.vdef5;
} 

/**
* ����vdef5��Setter����.���������Զ�����5
* ��������:2016-5-27
* @param newVdef5 java.lang.String
*/
public void setVdef5 ( String vdef5) {
this.vdef5=vdef5;
} 
 
/**
* ���� vdef6��Getter����.���������Զ�����6
*  ��������:2016-5-27
* @return java.lang.String
*/
public String getVdef6() {
return this.vdef6;
} 

/**
* ����vdef6��Setter����.���������Զ�����6
* ��������:2016-5-27
* @param newVdef6 java.lang.String
*/
public void setVdef6 ( String vdef6) {
this.vdef6=vdef6;
} 
 
/**
* ���� vdef7��Getter����.���������Զ�����7
*  ��������:2016-5-27
* @return java.lang.String
*/
public String getVdef7() {
return this.vdef7;
} 

/**
* ����vdef7��Setter����.���������Զ�����7
* ��������:2016-5-27
* @param newVdef7 java.lang.String
*/
public void setVdef7 ( String vdef7) {
this.vdef7=vdef7;
} 
 
/**
* ���� vdef8��Getter����.���������Զ�����8
*  ��������:2016-5-27
* @return java.lang.String
*/
public String getVdef8() {
return this.vdef8;
} 

/**
* ����vdef8��Setter����.���������Զ�����8
* ��������:2016-5-27
* @param newVdef8 java.lang.String
*/
public void setVdef8 ( String vdef8) {
this.vdef8=vdef8;
} 
 
/**
* ���� vdef9��Getter����.���������Զ�����9
*  ��������:2016-5-27
* @return java.lang.String
*/
public String getVdef9() {
return this.vdef9;
} 

/**
* ����vdef9��Setter����.���������Զ�����9
* ��������:2016-5-27
* @param newVdef9 java.lang.String
*/
public void setVdef9 ( String vdef9) {
this.vdef9=vdef9;
} 
 
/**
* ���� vdef10��Getter����.���������Զ�����10
*  ��������:2016-5-27
* @return java.lang.String
*/
public String getVdef10() {
return this.vdef10;
} 

/**
* ����vdef10��Setter����.���������Զ�����10
* ��������:2016-5-27
* @param newVdef10 java.lang.String
*/
public void setVdef10 ( String vdef10) {
this.vdef10=vdef10;
} 
 
/**
* ���� creator��Getter����.��������������
*  ��������:2016-5-27
* @return nc.vo.sm.UserVO
*/
public String getCreator() {
return this.creator;
} 

/**
* ����creator��Setter����.��������������
* ��������:2016-5-27
* @param newCreator nc.vo.sm.UserVO
*/
public void setCreator ( String creator) {
this.creator=creator;
} 
 
/**
* ���� creationtime��Getter����.������������ʱ��
*  ��������:2016-5-27
* @return nc.vo.pub.lang.UFDateTime
*/
public UFDateTime getCreationtime() {
return this.creationtime;
} 

/**
* ����creationtime��Setter����.������������ʱ��
* ��������:2016-5-27
* @param newCreationtime nc.vo.pub.lang.UFDateTime
*/
public void setCreationtime ( UFDateTime creationtime) {
this.creationtime=creationtime;
} 
 
/**
* ���� modifier��Getter����.������������޸���
*  ��������:2016-5-27
* @return nc.vo.sm.UserVO
*/
public String getModifier() {
return this.modifier;
} 

/**
* ����modifier��Setter����.������������޸���
* ��������:2016-5-27
* @param newModifier nc.vo.sm.UserVO
*/
public void setModifier ( String modifier) {
this.modifier=modifier;
} 
 
/**
* ���� modifiedtime��Getter����.������������޸�ʱ��
*  ��������:2016-5-27
* @return nc.vo.pub.lang.UFDateTime
*/
public UFDateTime getModifiedtime() {
return this.modifiedtime;
} 

/**
* ����modifiedtime��Setter����.������������޸�ʱ��
* ��������:2016-5-27
* @param newModifiedtime nc.vo.pub.lang.UFDateTime
*/
public void setModifiedtime ( UFDateTime modifiedtime) {
this.modifiedtime=modifiedtime;
} 
 
/**
* ���� ����ʱ�����Getter����.��������ʱ���
*  ��������:2016-5-27
* @return nc.vo.pub.lang.UFDateTime
*/
public UFDateTime getTs() {
return this.ts;
}
/**
* ��������ʱ�����Setter����.��������ʱ���
* ��������:2016-5-27
* @param newts nc.vo.pub.lang.UFDateTime
*/
public void setTs(UFDateTime ts){
this.ts=ts;
} 
     
    @Override
    public IVOMeta getMetaData() {
    return VOMetaFactory.getInstance().getVOMeta("uapbd.orderrelation");
    }
   }
    