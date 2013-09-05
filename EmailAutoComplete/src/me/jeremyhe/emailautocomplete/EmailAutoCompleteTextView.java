package me.jeremyhe.emailautocomplete;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AutoCompleteTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <ol>
 * <p>使用说明：
 * <li>复制{@link EmailAutoCompleteTextView}和{@link MailLoginDropDownListAdapter}到工程中
 * <li>复制layout文件夹下的mail_dropdown_item.xml到工程中对应目录
 * <li>复制drawble文件夹下mail_login_email_dropdownlist_*.xml到工程中对应文件夹
 * <li>复制drawble-hdpi文件夹下的mail_login_dropdown_divider.png到工程中对应文件夹
 * <li>复制values中styles.xml下的widget_dropdownlistview和widget_popupWindow样式到工程中对应的styles.xml文件
 * <li>复制values中themes.xml下的DropDownListView_NoScrollbar主题到工程中对应的themes.xml文件
 * <li>在androidmanifest.xml给使用此控件的Activity设置主题为android:theme="@style/DropDownListView_NoScrollbar"
 * </ol>
 * @author jeremyhe
 *
 */
public class EmailAutoCompleteTextView extends AutoCompleteTextView {
	
	private Context mContext;
	private MailLoginDropDownListAdapter<String> mEmailActvAdapter;
	private Drawable mClearDrawable;
	private Rect mBounds;
	
	private final String[] emails = new String[]{
			"qq.com",
			"163.com",
			"126.com",
			"sina.com",
			"vip.sina.com",
			"sina.cn",
			"hotmail.com",
			"outlook.com",
			"gmail.com",
			"sohu.com",
			"139.com",
			"wo.com.cn",
			"189.cn",
			"21cn.com",
			"yeah.net"
	};
	
	public EmailAutoCompleteTextView(Context context){
		this(context, null);
	}
	
	public EmailAutoCompleteTextView(Context context, AttributeSet attrs){
		super(context, attrs);
		mContext = context;
		initWidget();
	}
	
	private void initWidget(){
		mEmailActvAdapter = new MailLoginDropDownListAdapter<String>(mContext, R.layout.mail_dropdown_item);
		this.setAdapter(mEmailActvAdapter);
		this.addTextChangedListener(new EmailTextWatcher());
		
		this.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		this.setSingleLine(true);
		
		this.setDropDownVerticalOffset(dp2px(5));// TODO: 这个需要支持我们的多分辨率
		
		mClearDrawable = getContext().getResources().getDrawable(
				R.drawable.sync_progress_close);
		mClearDrawable.setBounds(0, 0, 45, 45);
	}
	
	
	private int dp2px(int dp){
		int density = getResources().getDisplayMetrics().densityDpi;
		return (int) (dp*(density/160.0f));
	}
	
	
	private class EmailTextWatcher implements TextWatcher {
	    private final Pattern mPattern = Pattern.compile("([^@]+)(@+)([^@]*)");
	    
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (s.length()==0 || mEmailActvAdapter == null) {
				return;
			}
			
			Matcher matcher = mPattern.matcher(s.toString());
			if (matcher.matches()) {
			    if (matcher.group(2).length() == 1 &&
			            TextUtils.isEmpty(matcher.group(3))) { // 表示以@结尾
			        mEmailActvAdapter.clear();			        
                    int len = emails.length;
                    for(int i=0;i<len;i++){
                        mEmailActvAdapter.add(matcher.group(1) + "@" + emails[i]);
                    }
			    }
			} else {
			    mEmailActvAdapter.clear();
			}
			
			mEmailActvAdapter.notifyDataSetChanged();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			 setEditTextDrawable();
		}
	}



	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}
	
	private void setEditTextDrawable() {
		if (getText().toString().length() == 0||!isFocused()) {
			setCompoundDrawables(null, null, null, null);
		} else {
			setCompoundDrawables(null, null, mClearDrawable, null);
		}
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		setEditTextDrawable();
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		mClearDrawable = null;
		mBounds = null;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if ((mClearDrawable != null)
				&& (event.getAction() == MotionEvent.ACTION_UP)) {
			mBounds = mClearDrawable.getBounds();
			int x = (int) event.getX();
			if (x > getRight() - 2 * mBounds.width()) {
				setText("");
				event.setAction(MotionEvent.ACTION_CANCEL);
			}
		}
		return super.onTouchEvent(event);
	}
}
