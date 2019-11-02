package CustomExceptions;

public class UserIdNotSet extends RuntimeException {

    public UserIdNotSet(){
        super("ID is not set fot this user");
    }

}
