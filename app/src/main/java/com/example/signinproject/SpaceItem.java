package com.example.signinproject;

//创建空间

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class SpaceItem extends DrawerItem<SpaceItem.ViewHolder> {

    //构造函数
    private int spaceDp;

    public SpaceItem(int spaceDp){
        this.spaceDp = spaceDp;
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        //创建空间视图
        Context c = parent.getContext();
        View view = new View(c);
        int height = (int) (c.getResources().getDisplayMetrics().density*spaceDp);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                height
        ));
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {

    }

    public class ViewHolder extends DrawerAdapter.ViewHolder{

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
