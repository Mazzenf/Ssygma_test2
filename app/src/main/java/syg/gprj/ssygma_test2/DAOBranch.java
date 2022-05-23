package syg.gprj.ssygma_test2;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOBranch
{
    private DatabaseReference databaseReference;
    public DAOBranch()
    {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference();

    }

    public Task<Void>  add(String id, branches brn)
    {
        return databaseReference.child("branches").child(id).setValue(brn);
    }

}
