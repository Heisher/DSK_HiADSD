/**
 * Created by Pawel on 21.05.2017.
 */
public class Response {
    public int senderID;
    public DiagnosticTree diagTree;

    public Response(int id)
    {
        senderID = id;
        diagTree = null;
    }

    public Response(int id, DiagnosticTree dTree)
    {
        senderID = id;
        diagTree = dTree;
    }
}
