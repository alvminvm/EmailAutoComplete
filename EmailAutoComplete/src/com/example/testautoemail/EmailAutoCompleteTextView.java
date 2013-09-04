package com.example.testautoemail;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;


/**
 * 使用说明：
 * <ol>
 * <li>复制EmailAutoCompleteTextView和{@link MailLoginDropDownListAdapter}到工程中</li>
 * <li>复制layout文件夹下的mail_dropdown_item.xml到工程中对应目录</li>
 * <li>复制drawble文件夹下mail_login_email_dropdownlist_*.xml到工程中对应文件夹</li>
 * <li>复制drawble-hdpi文件夹下的mail_login_dropdown_divider.png到工程中对应文件夹</li>
 * <li>复制values中styles.xml下的widget_dropdownlistview和widget_popupWindow样式到工程中对应的styles.xml文件</li>
 * <li>复制values中themes.xml下的DropDownListView_NoScrollbar主题到工程中对应的themes.xml文件</li>
 * <li>在androidmanifest.xml给使用此控件的Activity设置主题为android:theme="@style/DropDownListView_NoScrollbar"</li>
 * </ol>
 * @author jeremyhe
 *
 */
public class EmailAutoCompleteTextView extends AutoCompleteTextView {
	
	private Context mContext;
	private MailLoginDropDownListAdapter<String> mEmailActvAdapter;
	
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
	}
	
	
	private int dp2px(int dp){
		int density = getResources().getDisplayMetrics().densityDpi;
		return (int) (dp*(density/160.0f));
	}
	
	
	private class EmailTextWatcher implements TextWatcher{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (s.length()==0 || mEmailActvAdapter == null) {
				return;
			}
			
			char endChar = s.charAt(s.length()-1);
			if (s.length() == 1 && endChar == '@') {
				setText("");
				return;
			} 
			
			if (s.toString().contains("@")) {
				// 邮箱帐户已经输入，加后邮箱后缀提示给用户
				if(endChar == '@'){
					endChar = s.charAt(s.length()-2);
					if (endChar == '@') {
						setText(s.subSequence(0, s.length()-2));
						return;
					}
					
					mEmailActvAdapter.clear();
					int len = emails.length;
					for(int i=0;i<len;i++){
						mEmailActvAdapter.add(s+emails[i]);
					}
				}
			}
			else {
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
		}
	}



	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}
}
