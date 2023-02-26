package tools;

import java.util.*;

public class GamerMenager {
    public Map<String, GamerAccount> LoginBase = new HashMap<>();
    public Boolean checkcoreccness(String loginstring){
        String[] parsetab = loginstring.split(";");
        if((parsetab[0].compareTo("username")==0)&&(parsetab[2].compareTo("port")==0)&&(parsetab[4].compareTo("host")==0)){
            GamerAccount gamerAccount = new GamerAccount(parsetab[1], Integer.valueOf(parsetab[3]), parsetab[5]);
            if(LoginBase.containsKey(gamerAccount.username))return false;
            return true;
        }else return false;
    }


    public void add(GamerAccount gamerAccount){
        if(!LoginBase.containsKey(gamerAccount.username)){
            LoginBase.put(gamerAccount.username, gamerAccount);
        }
    }

    public String scores(){
        String str="";
        ArrayList<GamerAccount> list = new ArrayList<>();
        for(String u: LoginBase.keySet()) list.add(LoginBase.get(u));

        Collections.sort(list, new Comparator<GamerAccount>(){
            public int compare(GamerAccount o1, GamerAccount o2){
                if(o1.score == o2.score)
                    return 0;
                return o1.score < o2.score ? -1 : 1;
            }
        });
        
        int i = 0;
        for(GamerAccount u: list){
            i++;
            str = str + i +";"+u.username+";"+u.score+" ";
        }
        return  str.trim();
    }

}
