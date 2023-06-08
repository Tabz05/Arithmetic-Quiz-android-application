package com.tabish.arithmeticquiz;

public class User {

    public String user_name;
    public String email_id;
    public long hasImage;

    public long no_of_ques_attempt;
    public long no_of_ques_correct;

    public long no_of_ques_attempt_arcade;
    public long no_of_ques_correct_arcade;

    public User()
    {

    }

    public User(String user_name,String email)
    {
        this.user_name=user_name;
        this.email_id=email;
        this.hasImage=0;

        this.no_of_ques_attempt=0;
        this.no_of_ques_correct=0;

        this.no_of_ques_attempt_arcade=0;
        this.no_of_ques_correct_arcade=0;
    }
}
