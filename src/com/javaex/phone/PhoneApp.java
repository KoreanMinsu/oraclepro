package com.javaex.phone;

import java.util.List;
import java.util.Scanner;

public class PhoneApp {

	public static void main(String[] args) {

		PhoneDao phoneDao = new PhoneDao();
		List<PersonVo> personList;
		personList = phoneDao.getPersonList();
		
		Scanner sc = new Scanner(System.in);
		boolean flag = true;	
		
		System.out.println("****************************************************");
		System.out.println("*              전화번호 관리 프로그램              *");
		System.out.println("****************************************************");
		
		
		while(flag) {
			System.out.println();
			System.out.println("1.리스트  2.등록  3.수정  4.삭제  5.검색  6.종료");
			System.out.println("-------------------------------------------------");
			System.out.print(">메뉴번호: ");
			int num = sc.nextInt();

			switch(num) {
			case 1: 
				System.out.println("<1. 리스트>");
				loadList(phoneDao.getPersonList());
				
			case 2:	
				System.out.println("<2. 등록>");
				sc.nextLine();//개행문자
				
				System.out.print("이름 > ");
				String name = sc.nextLine();
				System.out.print("휴대전화 > ");
				String hp = sc.nextLine();
				System.out.print("회사번호 > ");
				String company = sc.nextLine();
				
				int iCount = phoneDao.PersonInsert(new PersonVo(name, hp, company));
				if(iCount>0) {
					System.out.println("["+iCount+"건 등록되었습니다.]");
				}else {
					System.out.println("문의바람"+iCount);
				}
				
			case 3:
				System.out.println("<3. 수정>");
				sc.nextLine();//개행문자
				
				System.out.print("이름 > ");
				String uName = sc.nextLine();
				System.out.print("휴대전화 > ");
				String uHp = sc.nextLine();
				System.out.print("회사번호 > ");
				String uCompany = sc.nextLine();
				int uCount = phoneDao.PersonUpdate(new PersonVo(uName, uHp, uCompany));
				if(uCount>0) {
					System.out.println("["+uCount+"건 수정되었습니다.]");
				}else {
					System.out.println("문의바람"+uCount);
				}
				
			case 4:
				System.out.println("<4. 삭제>");
				System.out.print(">번호: ");
				int dNum = sc.nextInt();
				int dCount = phoneDao.PersonDelete(dNum);
				if(dCount>0) {
					System.out.println("["+dCount+"건 삭제되었습니다.]");
				}else {
					System.out.println("문의바람"+dCount);
				}
			case 5:
				System.out.println("<5. 검색>");
				System.out.print(">검색어: ");

				String searchWord = sc.nextLine();
				loadList(phoneDao.SearchedList(searchWord));
				
			case 6:
				System.out.println("***********************************");
				System.out.println("*           감사합니다.           *");
				System.out.println("***********************************");
				flag = false;
				break;
			default :
				System.out.println("[다시 입력해주세요.]");
				
			}
		}

		
		sc.close();
	}
	
	public static void loadList(List<PersonVo> personList) {
		for(int i=0;i<personList.size(); i++) {
			PersonVo personVo = personList.get(i);
			System.out.println(personVo.getPersonId()+".\t"+personVo.getName()+"\t"+personVo.getHp()+"\t"+personVo.getCompany());;
		}
		System.out.println();
	}
}
