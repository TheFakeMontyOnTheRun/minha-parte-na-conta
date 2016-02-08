package br.odb.myshare;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.text.NumberFormat;

/**
 * Created by danielmonteiro on 10/2/15.
 */
public class EditPrice extends EditText {

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            EditPrice.this.removeTextChangedListener(this);

            String cleanString = s.toString().replace(".00", "").replaceAll("[$,.[^\\d.]]", "");

            if (cleanString.length() > 0) {
                float parsed = Float.parseFloat(cleanString);
                String formatted = NumberFormat.getCurrencyInstance().format( parsed );
                EditPrice.this.setText(formatted);
                EditPrice.this.setSelection(formatted.length());
            }

            EditPrice.this.addTextChangedListener(this);
        }
        public void afterTextChanged(Editable s) {
        }
    };


    public EditPrice(Context context) {
        super(context);
        init();
    }

    public EditPrice(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditPrice(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setRawInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        addTextChangedListener(watcher);
    }
}
