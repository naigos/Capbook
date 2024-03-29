package com.example.proyecto_final;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

//CLASE MODIFICADA QUE EXTIENDE DE SCROLLVIEW PARA PODER METER UN LISTVIEW EN UN SCROLLVIEW
public class VerticalScrollView extends ScrollView {

	public VerticalScrollView(Context context) {
		super(context);
	}

	public VerticalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VerticalScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			super.onTouchEvent(ev);
			break;

		case MotionEvent.ACTION_MOVE:
			return false; // redirect MotionEvents to ourself

		case MotionEvent.ACTION_CANCEL:
			super.onTouchEvent(ev);
			break;

		case MotionEvent.ACTION_UP:
			return false;

		default:
			break;
		}

		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		super.onTouchEvent(ev);
		return true;
	}
}
