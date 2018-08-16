package utils;

import java.util.Scanner;

public class Student {
	Scanner sc=new Scanner(System.in);
	String name;
	int student_number;
	int grade;
	
	
    int n;
	public Student(int n){
		this.n=n;
	}
	String [][] a=new String[n][3];
	public void input(){
		System.out.print("«Î ‰»Î–≈œ¢£∫");
		a[0][0] = "jlkj";
		for(int i=0;i<n;i++){
			for(int j=0;j<3;j++){
				//String string = sc.next();
				//a[i][j] = string;
				a[i][j]=sc.next();
			}
		}
	}
	public void compare(){
		int[] b=new int[n];
		for(int i=0;i<n;i++){
			b[i]=a[n][3].charAt(0)-'0';
		}
	}
	public void output(int m,int n){
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				System.out.print(a[i][j]+" ");
			}
			System.out.println();
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student a=new Student(3);
		a.input();
		a.output(3,3);

	}
}
