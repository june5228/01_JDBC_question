package run;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static common.JDBCTemplate.closes;
import static common.JDBCTemplate.getConnection;

public class deleteEmployeeInfo {

    // 삭제할 사원의 이름을 사용자로부터 입력받아 삭제
    // 이름이 정확히 일치해야 함
    // 사원 삭제에 성공하면 "[이름]님이 퇴출되었습니다." 출력
    // 사원 삭제에 실패하면 "[이름]님은 퇴출될 수 없습니다." 출력
    public static void main(String[] args) {
        Connection con = getConnection();

        PreparedStatement pstmt = null;
        int result = 0;
        String name = null;
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("C:\\java22\\01_JDBC_question\\jdbc-practice-source\\src\\main\\java\\mapper\\employee-query.xml"));
            String query = prop.getProperty("delete");

            Scanner sc = new Scanner(System.in);
            System.out.print("삭제할 사원의 이름을 입력 해 주세요 : ");
            name = sc.nextLine();

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);

            result = pstmt.executeUpdate();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closes(con);
            closes(pstmt);
        }
        if(result > 0){
            System.out.println(name + "님이 퇴출 되었습니다.");
        } else {
            System.out.println(name + "님은 퇴출할 수 없습니다.");
        }
    }
}
