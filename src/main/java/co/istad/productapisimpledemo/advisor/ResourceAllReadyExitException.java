package co.istad.productapisimpledemo.advisor;

public class ResourceAllReadyExitException extends RuntimeException{
    public ResourceAllReadyExitException(String message){
        super(message);
    }
}
