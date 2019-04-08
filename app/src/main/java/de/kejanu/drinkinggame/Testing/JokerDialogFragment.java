package de.kejanu.drinkinggame.Testing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import de.kejanu.drinkinggame.Joker;
import de.kejanu.drinkinggame.R;

public class JokerDialogFragment extends DialogFragment {

    private View v;

    // The Activity that creates an instance of this dialog
    // must implement the following interface, to be able to
    // receive event callbacks.

    // Each method passes the Dialog in case the host needs to query it.
    public interface JokerDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, View v);
        public void onDialogNegativeClick(DialogFragment dialog, View v);
    }

    // This instance of the interface will be used to deliver action events
    JokerDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate
    // the JokerDialogListener

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the JokerDialogListener so we can send
            // events to the host
            listener = (JokerDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement JokerDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(v != null ? "Den Joker: \"" + ((Joker)v.getTag()).getText() + "\" einsetzen?" : "ERROR")
               .setTitle(R.string.alertdialog_joker_header)
               .setPositiveButton(R.string.alertdialog_joker_ja, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       // Send the positve button event back to the host activity
                       listener.onDialogPositiveClick(JokerDialogFragment.this, v);
                   }
               })
               .setNegativeButton(R.string.alertdialog_joker_nein, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       // Send the negative button event back to the host activity
                       listener.onDialogNegativeClick(JokerDialogFragment.this, v);
                   }
               });
        return builder.create();
    }

    public void setV(View v) {
        this.v = v;
    }
}











