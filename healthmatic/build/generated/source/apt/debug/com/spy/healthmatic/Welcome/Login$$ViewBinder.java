// Generated code from Butter Knife. Do not modify!
package com.spy.healthmatic.Welcome;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Login$$ViewBinder<T extends com.spy.healthmatic.Welcome.Login> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558528, "field 'loginButton'");
    target.loginButton = finder.castView(view, 2131558528, "field 'loginButton'");
    view = finder.findRequiredView(source, 2131558526, "field 'editTxtEmail'");
    target.editTxtEmail = finder.castView(view, 2131558526, "field 'editTxtEmail'");
    view = finder.findRequiredView(source, 2131558527, "field 'editTxtPassword'");
    target.editTxtPassword = finder.castView(view, 2131558527, "field 'editTxtPassword'");
  }

  @Override public void unbind(T target) {
    target.loginButton = null;
    target.editTxtEmail = null;
    target.editTxtPassword = null;
  }
}
