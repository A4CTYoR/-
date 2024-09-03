// 学生(用户)管理系统
import java.util.*;
public class App {
    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("========================================");
            System.out.println("欢迎来到学生管理系统");
            System.out.println("请选择操作1登录 2注册 3忘记密码 4查询用户信息 5查询验证码");
            String choose = sc.next();
            switch (choose) {
                case "1" -> userLogin(list);
                case "2" -> registerUser(list);
                case "3" -> forgotPassword(list);
                case "4" -> queryUserInfo(list);
                case "5" -> userLoginCode();
                default -> System.out.println("没有这个选项");
            }
        }
    }

    // 重置密码
    public static void forgotPassword(ArrayList<User> list){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入您要找回的用户名");
        String username = sc.next();
        // checkUserLoginNameIndex还是需要先确认用户名是否存在并返回索引
        int userindex = checkUserLoginNameIndex(list,username);
        // System.out.println(userindex);
        if(userindex >= 0){
            // 给予3次验证过程
            for (int i = 3; i > 0; ) {
                System.out.println("请验证您的身份证号");
                String personid = sc.next();
                System.out.println("请验证您的手机号");
                String phoneNumber = sc.next();
                // 确认好用户存在后继续验证身份证号和手机号
                // checkIDandNum对比集合中对应索引的身份证号和手机号是否同时匹配正确才行
                if(checkIDandNum(list,userindex,personid,phoneNumber)){
                    // 验证成功后重置密码
                    User us = list.get(userindex);
                    System.out.println("请输入要重置的密码");
                    String passowrd = sc.next();
                    // 直接对应索引的对象修改值
                    us.setPassword(passowrd);
                    System.out.println("修改成功");
                    break;
                }else{
                    // 记录错误次数
                    i--;
                    System.out.println("身份证号或者手机号信息有误");  
                    // 100000000000000000   
                    if(i == 0){
                        System.out.println("错误次数太多,请稍后再试");
                    }
                }
            }            
        }else{
            System.out.println("未注册该用户");
        }
    }

    // 查找到集合对应得索引对象,匹配传入的省份证号和手机号的值是否同时匹配正确
    public static boolean checkIDandNum(ArrayList<User> list,int userindex,String personid,String phoneNumber){
        User us = list.get(userindex);
        String id = us.getPersonid();
        String phone = us.getPhoneNumber();

        if(personid.equals(id) && phoneNumber.equals(phone)){            
            return true;
        }
        return false;
    }

    // 用户登录
    public static void userLogin(ArrayList<User> list){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名");
        String username = sc.next();
        // 判断用户名是否存在于集合checkUserLogin
        if (checkUserLogin(list,username)) {
            // 获取该用户名的索引
            int userindex = checkUserLoginNameIndex(list,username);
            // 设置该用户登录密码为3次机会
            for (int i = 2; i >= 0; i--) {
                System.out.println("请输入密码");
                String password = sc.next();
                // userLoginCode验证码生成
                String code = userLoginCode();
                System.out.println("验证码: " + code);
                // 输入你的验证码
                System.out.println("请输入验证码");
                String usercode = sc.next();
                // 对比验证码是否一致
                if(usercode.equals(code)){
                    // checkUserLoginPasswd判断该索引用户的密码是否一致,一致则登录成功
                    if(checkUserLoginPasswd(list,userindex,password)){
                        System.out.println("登录成功,欢迎您" + username);
                        break;
                        // i为0表结束
                    }else if(i == 0){
                        break;
                    }else{
                        // 计算错误机会
                        System.out.println("密码错误,还有"+ i +"次机会");
                    }
                }else{
                    // 提示验证码不一致也会进行判断
                    if(i == 0){
                        System.out.println("请稍后再试");
                        break;
                    }else{
                        System.out.println("验证码错误,还有"+ i +"次机会");
                    }
                }
                  
            }
        }
    }

    // 登录验证码生成,4位大小写字母+1位数字组合
    public static String userLoginCode(){
        // 录入大小写英文字母
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
        // codeNumber生成随机数字
        char codenum = codeNumber();

        // 随机取出字符拼接为4位字符串
        for (int i = 0; i < 4; i++) {
            int radomindex = r.nextInt(c.length);
            letter += c[radomindex]; 
        }
        // 拼接为完整验证码
        letter += codenum;
        // 将其再次变为数组,并打乱位置
        char[] result = letter.toCharArray();
        for (int i = 0; i < result.length; i++) {
            // 选择数字,再随机选择一个字母,交换位置
            int randomnum = r.nextInt(result.length);
            char temp = result[result.length - 1];
            result[result.length - 1] = result[randomnum];
            result[randomnum] = temp;
        }   
        // 得到结果后新建为String对象返回
        String end = new String(result);
        return end;
    }

    // 生成随机数字
    public static char codeNumber(){
        char[] c = new char[10];
        for (int i = 0; i < c.length; i++) {
            c[i] = (char)('0' + i);
        }

        Random r = new Random();
        int radomindex = r.nextInt(c.length);
        return c[radomindex];
    }

    // 确认两次输入的密码是否一致
    public static boolean checkUserLoginPasswd(ArrayList<User> list,int userindex,String password){
        User us = list.get(userindex);
        String passwd = us.getPassword();
        if(passwd.equals(password)){
            return true;
        }
        return false;
    }

    // 确认用户是否存在并返回索引
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
            System.out.println("未注册,请先注册");
            return false;
        }
    }

    // 注册用户
    public static void registerUser(ArrayList<User> list){
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("请输入用户名(长度限制为3~15位且只能为大小写字母和数字的组合)");
            System.out.println("输入0返回菜单");
            System.out.println("请输入用户名");
            String username = sc.next();

            // 设置一个返回主菜单功能
            if(username.equals("0")){
                break;
            }

            // 集合长度为0直接录入不查询
            if(list.size() == 0){
                // checkUsername确认用户名的命名规则
                if(checkUsername(username)){
                    registerInfo(list,username);
                    break;
                }else{
                    System.out.println("----用户名不允许包含!@#$%^&*()+-*此类特殊字符且用户名必须由大小写字母配合数字的组合----");
                }
            }else{
                // 集合长度不为0,需要先查询用户名的信息,是否存在,存在则重新录入
                // checkUsernameIndex方法查询用户名信息
                if(checkUsernameIndex(list, username)){
                    // checkUsername确认用户名的命名规则
                    if(checkUsername(username)){
                        // 3.registerInfo方法前面符合要求后,开始录入其他信息
                        registerInfo(list,username);
                        break;
                    }
                }else{
                    System.out.println("存在该用户名,请重新输入");
                }
            }
        }  
    }

    // 确认创建的用户名在集合中是否存在
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

    // 确认创建的用户名
    public static boolean checkUsername(String username){
        char[] c = username.toCharArray();
        if(checkUsernameLetter(username,c) && checkUsernameNum(username,c)){
            return true;
        }
        return false;
    }

    // 确认注册用户名是否为3~15位,并且确认包含大写或者小写字母
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

    // 确认用户名是否包含数字
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

    // 前面的规则符合后录入其它信息
    public static void registerInfo(ArrayList<User> list,String username){
        Scanner sc = new Scanner(System.in);
        User userinfo = new User();

        userinfo.setUsername(username);

        // 设置密码
        while (true) {
            System.out.println("请输入密码");
            String password = sc.next();
            // checkPassword方法设定  需要两次输入一致,然后才录入密码
            if(checkPassword(password)){
                System.out.println("密码设置成功");
                userinfo.setPassword(password);
                break;
            }else{
                System.out.println("请输入两次一致的密码");
            }
        }

        // 设置身份证号
        while (true) {
            System.out.println("请输入身份证号");
            String personid = sc.next();
            // 满足身份证号首位不为0，且18位,只有最后一位可以为数字或者大小写X或x
            if (checkPersonid(personid)) {
                System.out.println("身份证号设置成功");
                userinfo.setPersonid(personid);
                break;
            }else{
                System.out.println("身份证号开头不能为0或者身份证位数(18位)有错误,请重新输入");
            }
        }
        // 000000000000000000 测试身份证号录入

        // 设置手机号
        while (true) {
            System.out.println("请输入手机号");
            String phoneNumer = sc.next();
            // 设置手机号长度为11且首位不能为0,且只包含数字
            if(checkPhoneNumber(phoneNumer)){
                System.out.println("手机号录入成功");
                userinfo.setPhoneNumber(phoneNumer);
                break;
            }else{
                System.out.println("输入的手机号错误,手机号为11位且首位不为0,不包含特殊字符");
            }
        }
        // 11111111111 测试手机号录入

        // 以上信息满足则添加到集合
        list.add(userinfo);
        System.out.println("注册成功");
    }

    // 注册用户的手机号满足11位,且首位不为0,并且确认11位都为数字
    public static boolean checkPhoneNumber(String phoneNumer){
        char[] c = phoneNumer.toCharArray();
        // 判断手机号长度为11位,否则false
        if(c.length == 11){
            // 如果第一位为0则会退回到上一步
            if(c[0] == '0'){
                System.out.println("手机号不能以0开头");
            }else{
                int count = 0;
                // 判断每一位是否为数字0~9之间
                for (int i = 0; i < c.length; i++) {
                    if(c[i] >= '0' && c[i] <= '9'){
                        count++;
                    }
                }
                // 确认后面11位都为数字
                if(count == 11){
                    return true;
                }
            }

        }
        return false;
    }

    // 设置身份证号
    public static boolean checkPersonid(String personid){
        // 将身份证号转换为数组去判断
        char[] c = personid.toCharArray();
        // 满足长度为18位,否则false
        if(c.length == 18){
            // 如果首位字符为0,则返回false
            if (c[0] == '0') {
                return false;
            }else{
                // 遍历每个字符,1~17位置满足都为数字,然后判断最后一位    
                if(checkid(c) == 17){
                    // 判断最后一位是否满足要求,最后一位可以为数字或者大小写的字母 'x'或'X'
                    if((c[17] >= '0' && c[17] <= '9') || c[17] == 'X' || c[17] == 'x'){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    

    // 遍历1~17位的身份证号是否全部满足为数字
    public static int checkid(char[] c){
        int count = 0;
        for (int i = 0; i < c.length - 1; i++) {
            if(c[i] >= '0' && c[i] <= '9'){
                count++;
            }
        }   
        return count;
    }

    // 确认两次输入的密码是否一致
    public static boolean checkPassword(String password){
        Scanner sc = new Scanner(System.in);
        System.out.println("确认密码");
        String passwordtwo = sc.next();
        if(password.equals(passwordtwo)){
            return true;
        }
        return false;
    }

    // 查询用户信息
    public static void queryUserInfo(ArrayList<User> list){
        for (int i = 0; i < list.size(); i++) {
            User usinfo = list.get(i);
            System.out.println("用户名为:" + usinfo.getUsername() + "用户密码为:" + usinfo.getPassword() +
            "用户身份证号为:" + usinfo.getPersonid() + "用户手机号为:" + usinfo.getPhoneNumber());
        }
    }

}
