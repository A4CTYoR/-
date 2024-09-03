// ѧ��(�û�)����ϵͳ
import java.util.*;
public class App {
    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("========================================");
            System.out.println("��ӭ����ѧ������ϵͳ");
            System.out.println("��ѡ�����1��¼ 2ע�� 3�������� 4��ѯ�û���Ϣ 5��ѯ��֤��");
            String choose = sc.next();
            switch (choose) {
                case "1" -> userLogin(list);
                case "2" -> registerUser(list);
                case "3" -> forgotPassword(list);
                case "4" -> queryUserInfo(list);
                case "5" -> userLoginCode();
                default -> System.out.println("û�����ѡ��");
            }
        }
    }

    // ��������
    public static void forgotPassword(ArrayList<User> list){
        Scanner sc = new Scanner(System.in);
        System.out.println("��������Ҫ�һص��û���");
        String username = sc.next();
        // checkUserLoginNameIndex������Ҫ��ȷ���û����Ƿ���ڲ���������
        int userindex = checkUserLoginNameIndex(list,username);
        // System.out.println(userindex);
        if(userindex >= 0){
            // ����3����֤����
            for (int i = 3; i > 0; ) {
                System.out.println("����֤�������֤��");
                String personid = sc.next();
                System.out.println("����֤�����ֻ���");
                String phoneNumber = sc.next();
                // ȷ�Ϻ��û����ں������֤���֤�ź��ֻ���
                // checkIDandNum�Աȼ����ж�Ӧ���������֤�ź��ֻ����Ƿ�ͬʱƥ����ȷ����
                if(checkIDandNum(list,userindex,personid,phoneNumber)){
                    // ��֤�ɹ�����������
                    User us = list.get(userindex);
                    System.out.println("������Ҫ���õ�����");
                    String passowrd = sc.next();
                    // ֱ�Ӷ�Ӧ�����Ķ����޸�ֵ
                    us.setPassword(passowrd);
                    System.out.println("�޸ĳɹ�");
                    break;
                }else{
                    // ��¼�������
                    i--;
                    System.out.println("���֤�Ż����ֻ�����Ϣ����");  
                    // 100000000000000000   
                    if(i == 0){
                        System.out.println("�������̫��,���Ժ�����");
                    }
                }
            }            
        }else{
            System.out.println("δע����û�");
        }
    }

    // ���ҵ����϶�Ӧ����������,ƥ�䴫���ʡ��֤�ź��ֻ��ŵ�ֵ�Ƿ�ͬʱƥ����ȷ
    public static boolean checkIDandNum(ArrayList<User> list,int userindex,String personid,String phoneNumber){
        User us = list.get(userindex);
        String id = us.getPersonid();
        String phone = us.getPhoneNumber();

        if(personid.equals(id) && phoneNumber.equals(phone)){            
            return true;
        }
        return false;
    }

    // �û���¼
    public static void userLogin(ArrayList<User> list){
        Scanner sc = new Scanner(System.in);
        System.out.println("�������û���");
        String username = sc.next();
        // �ж��û����Ƿ�����ڼ���checkUserLogin
        if (checkUserLogin(list,username)) {
            // ��ȡ���û���������
            int userindex = checkUserLoginNameIndex(list,username);
            // ���ø��û���¼����Ϊ3�λ���
            for (int i = 2; i >= 0; i--) {
                System.out.println("����������");
                String password = sc.next();
                // userLoginCode��֤������
                String code = userLoginCode();
                System.out.println("��֤��: " + code);
                // ���������֤��
                System.out.println("��������֤��");
                String usercode = sc.next();
                // �Ա���֤���Ƿ�һ��
                if(usercode.equals(code)){
                    // checkUserLoginPasswd�жϸ������û��������Ƿ�һ��,һ�����¼�ɹ�
                    if(checkUserLoginPasswd(list,userindex,password)){
                        System.out.println("��¼�ɹ�,��ӭ��" + username);
                        break;
                        // iΪ0�����
                    }else if(i == 0){
                        break;
                    }else{
                        // ����������
                        System.out.println("�������,����"+ i +"�λ���");
                    }
                }else{
                    // ��ʾ��֤�벻һ��Ҳ������ж�
                    if(i == 0){
                        System.out.println("���Ժ�����");
                        break;
                    }else{
                        System.out.println("��֤�����,����"+ i +"�λ���");
                    }
                }
                  
            }
        }
    }

    // ��¼��֤������,4λ��Сд��ĸ+1λ�������
    public static String userLoginCode(){
        // ¼���СдӢ����ĸ
        char[] c = new char[52];
        for (int i = 0; i < c.length; i++) {
            if(i <= 25){
                c[i] = (char)(65 + i);
            }else if(i > 25 && i<= 51){
                c[i] = (char)(97 + i - 26);
            }
        }

        String letter = "";    
        Random r = new Random();
        // codeNumber�����������
        char codenum = codeNumber();

        // ���ȡ���ַ�ƴ��Ϊ4λ�ַ���
        for (int i = 0; i < 4; i++) {
            int radomindex = r.nextInt(c.length);
            letter += c[radomindex]; 
        }
        // ƴ��Ϊ������֤��
        letter += codenum;
        // �����ٴα�Ϊ����,������λ��
        char[] result = letter.toCharArray();
        for (int i = 0; i < result.length; i++) {
            // ѡ������,�����ѡ��һ����ĸ,����λ��
            int randomnum = r.nextInt(result.length);
            char temp = result[result.length - 1];
            result[result.length - 1] = result[randomnum];
            result[randomnum] = temp;
        }   
        // �õ�������½�ΪString���󷵻�
        String end = new String(result);
        return end;
    }

    // �����������
    public static char codeNumber(){
        char[] c = new char[10];
        for (int i = 0; i < c.length; i++) {
            c[i] = (char)('0' + i);
        }

        Random r = new Random();
        int radomindex = r.nextInt(c.length);
        return c[radomindex];
    }

    // ȷ����������������Ƿ�һ��
    public static boolean checkUserLoginPasswd(ArrayList<User> list,int userindex,String password){
        User us = list.get(userindex);
        String passwd = us.getPassword();
        if(passwd.equals(password)){
            return true;
        }
        return false;
    }

    // ȷ���û��Ƿ���ڲ���������
    public static int checkUserLoginNameIndex(ArrayList<User> list,String username){
        for (int i = 0; i < list.size(); i++) {
            User us = list.get(i);
            String uname = us.getUsername();
            if(uname.equals(username)){
                return i;
            }
        }
        return -1;
    }

    public static boolean checkUserLogin(ArrayList<User> list,String username){
        if(!checkUsernameIndex(list,username)){
            return true;
        }else{
            System.out.println("δע��,����ע��");
            return false;
        }
    }

    // ע���û�
    public static void registerUser(ArrayList<User> list){
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("�������û���(��������Ϊ3~15λ��ֻ��Ϊ��Сд��ĸ�����ֵ����)");
            System.out.println("����0���ز˵�");
            System.out.println("�������û���");
            String username = sc.next();

            // ����һ���������˵�����
            if(username.equals("0")){
                break;
            }

            // ���ϳ���Ϊ0ֱ��¼�벻��ѯ
            if(list.size() == 0){
                // checkUsernameȷ���û�������������
                if(checkUsername(username)){
                    registerInfo(list,username);
                    break;
                }else{
                    System.out.println("----�û������������!@#$%^&*()+-*���������ַ����û��������ɴ�Сд��ĸ������ֵ����----");
                }
            }else{
                // ���ϳ��Ȳ�Ϊ0,��Ҫ�Ȳ�ѯ�û�������Ϣ,�Ƿ����,����������¼��
                // checkUsernameIndex������ѯ�û�����Ϣ
                if(checkUsernameIndex(list, username)){
                    // checkUsernameȷ���û�������������
                    if(checkUsername(username)){
                        // 3.registerInfo����ǰ�����Ҫ���,��ʼ¼��������Ϣ
                        registerInfo(list,username);
                        break;
                    }
                }else{
                    System.out.println("���ڸ��û���,����������");
                }
            }
        }  
    }

    // ȷ�ϴ������û����ڼ������Ƿ����
    public static boolean checkUsernameIndex(ArrayList<User> list,String username){
        for (int i = 0; i < list.size(); i++) {
            User us = list.get(i);
            String uname = us.getUsername();
            if(uname.equals(username)){
                return false;
            }
        }
        return true;
    }

    // ȷ�ϴ������û���
    public static boolean checkUsername(String username){
        char[] c = username.toCharArray();
        if(checkUsernameLetter(username,c) && checkUsernameNum(username,c)){
            return true;
        }
        return false;
    }

    // ȷ��ע���û����Ƿ�Ϊ3~15λ,����ȷ�ϰ�����д����Сд��ĸ
    public static boolean checkUsernameLetter(String username,char[] c){
        int count = 0;
        if(username.length() >= 3 && username.length() <= 15){
            for (int i = 0; i < c.length; i++) {
                if((c[i] >= 'a' && c[i] <= 'z') || (c[i] >= 'A' && c[i] <= 'Z')){
                    count++;
                }
            }
        }
        if(count > 0){
            return true;
        }
        return false;
    }

    // ȷ���û����Ƿ��������
    public static boolean checkUsernameNum(String username,char[] c){
        int count = 0;
        for (int i = 0; i < c.length; i++) {
            if(c[i] >= '0' && c[i] <='9'){
                count++;
            }
        }
        if(count > 0){
            return true;
        }
        return false;
    }

    // ǰ��Ĺ�����Ϻ�¼��������Ϣ
    public static void registerInfo(ArrayList<User> list,String username){
        Scanner sc = new Scanner(System.in);
        User userinfo = new User();

        userinfo.setUsername(username);

        // ��������
        while (true) {
            System.out.println("����������");
            String password = sc.next();
            // checkPassword�����趨  ��Ҫ��������һ��,Ȼ���¼������
            if(checkPassword(password)){
                System.out.println("�������óɹ�");
                userinfo.setPassword(password);
                break;
            }else{
                System.out.println("����������һ�µ�����");
            }
        }

        // �������֤��
        while (true) {
            System.out.println("���������֤��");
            String personid = sc.next();
            // �������֤����λ��Ϊ0����18λ,ֻ�����һλ����Ϊ���ֻ��ߴ�СдX��x
            if (checkPersonid(personid)) {
                System.out.println("���֤�����óɹ�");
                userinfo.setPersonid(personid);
                break;
            }else{
                System.out.println("���֤�ſ�ͷ����Ϊ0�������֤λ��(18λ)�д���,����������");
            }
        }
        // 000000000000000000 �������֤��¼��

        // �����ֻ���
        while (true) {
            System.out.println("�������ֻ���");
            String phoneNumer = sc.next();
            // �����ֻ��ų���Ϊ11����λ����Ϊ0,��ֻ��������
            if(checkPhoneNumber(phoneNumer)){
                System.out.println("�ֻ���¼��ɹ�");
                userinfo.setPhoneNumber(phoneNumer);
                break;
            }else{
                System.out.println("������ֻ��Ŵ���,�ֻ���Ϊ11λ����λ��Ϊ0,�����������ַ�");
            }
        }
        // 11111111111 �����ֻ���¼��

        // ������Ϣ��������ӵ�����
        list.add(userinfo);
        System.out.println("ע��ɹ�");
    }

    // ע���û����ֻ�������11λ,����λ��Ϊ0,����ȷ��11λ��Ϊ����
    public static boolean checkPhoneNumber(String phoneNumer){
        char[] c = phoneNumer.toCharArray();
        // �ж��ֻ��ų���Ϊ11λ,����false
        if(c.length == 11){
            // �����һλΪ0����˻ص���һ��
            if(c[0] == '0'){
                System.out.println("�ֻ��Ų�����0��ͷ");
            }else{
                int count = 0;
                // �ж�ÿһλ�Ƿ�Ϊ����0~9֮��
                for (int i = 0; i < c.length; i++) {
                    if(c[i] >= '0' && c[i] <= '9'){
                        count++;
                    }
                }
                // ȷ�Ϻ���11λ��Ϊ����
                if(count == 11){
                    return true;
                }
            }

        }
        return false;
    }

    // �������֤��
    public static boolean checkPersonid(String personid){
        // �����֤��ת��Ϊ����ȥ�ж�
        char[] c = personid.toCharArray();
        // ���㳤��Ϊ18λ,����false
        if(c.length == 18){
            // �����λ�ַ�Ϊ0,�򷵻�false
            if (c[0] == '0') {
                return false;
            }else{
                // ����ÿ���ַ�,1~17λ�����㶼Ϊ����,Ȼ���ж����һλ    
                if(checkid(c) == 17){
                    // �ж����һλ�Ƿ�����Ҫ��,���һλ����Ϊ���ֻ��ߴ�Сд����ĸ 'x'��'X'
                    if((c[17] >= '0' && c[17] <= '9') || c[17] == 'X' || c[17] == 'x'){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    

    // ����1~17λ�����֤���Ƿ�ȫ������Ϊ����
    public static int checkid(char[] c){
        int count = 0;
        for (int i = 0; i < c.length - 1; i++) {
            if(c[i] >= '0' && c[i] <= '9'){
                count++;
            }
        }   
        return count;
    }

    // ȷ����������������Ƿ�һ��
    public static boolean checkPassword(String password){
        Scanner sc = new Scanner(System.in);
        System.out.println("ȷ������");
        String passwordtwo = sc.next();
        if(password.equals(passwordtwo)){
            return true;
        }
        return false;
    }

    // ��ѯ�û���Ϣ
    public static void queryUserInfo(ArrayList<User> list){
        for (int i = 0; i < list.size(); i++) {
            User usinfo = list.get(i);
            System.out.println("�û���Ϊ:" + usinfo.getUsername() + "�û�����Ϊ:" + usinfo.getPassword() +
            "�û����֤��Ϊ:" + usinfo.getPersonid() + "�û��ֻ���Ϊ:" + usinfo.getPhoneNumber());
        }
    }

}
