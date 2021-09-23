package teqTestData;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import teqUtil.XLUtils;

public class TeqTestDataProvider {

	public static String path;
	
	public static void Filelocation() {
	if(System.getProperty("os.name").toLowerCase().contains("windows")) 
	{	
	path=System.getProperty("user.dir")+"\\src\\test\\java\\teqTestData\\TeqtestData.xlsx";
	}
	
	else if(System.getProperty("os.name").toLowerCase().contains("mac"))
	{
		path=System.getProperty("user.dir")+"/src/test/java/teqTestData/TeqtestData.xlsx";
	}
	}
	
	
	@DataProvider(name="Signup")
	String [][] getData() throws IOException
	{
		
		Filelocation();
		int rownum=XLUtils.getRowCount(path, "Signup");
		int colcount=XLUtils.getCellCount(path,"Signup",1);
		
		String Signup[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				Signup[i-1][j]=XLUtils.getCellData(path,"Signup", i,j);//1 0
				
			}
				
		}
		
	return Signup;
	}
	
	

	
}
