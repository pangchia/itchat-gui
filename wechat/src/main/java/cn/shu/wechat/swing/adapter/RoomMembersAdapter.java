package cn.shu.wechat.swing.adapter;

import cn.shu.wechat.api.ContactsTools;
import cn.shu.wechat.beans.pojo.Contacts;
import cn.shu.wechat.core.Core;
import cn.shu.wechat.swing.app.Launcher;
import cn.shu.wechat.swing.components.Colors;
import cn.shu.wechat.swing.components.UserInfoPopup;
import cn.shu.wechat.swing.db.model.CurrentUser;
import cn.shu.wechat.swing.db.service.ContactsUserService;
import cn.shu.wechat.swing.db.service.CurrentUserService;
import cn.shu.wechat.swing.listener.AbstractMouseListener;
import cn.shu.wechat.swing.panels.RoomMembersPanel;
import cn.shu.wechat.swing.utils.AvatarUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by 舒新胜 on 07/06/2017.
 */
public class RoomMembersAdapter extends BaseAdapter<RoomMembersItemViewHolder> {
    private final List<Contacts> members;
    private final List<RoomMembersItemViewHolder> viewHolders = new ArrayList<>();

    private MouseAdapter addMemberButtonMouseListener;
    private MouseAdapter removeMemberButtonMouseListener;
    /**
     * 当前选中的viewHolder
     */
    private RoomMembersItemViewHolder selectedViewHolder;
    public RoomMembersAdapter(List<Contacts> members) {
        this.members = members;
    }

    @Override
    public RoomMembersItemViewHolder onCreateViewHolder(int viewType, int position) {
        return new RoomMembersItemViewHolder();
    }

    @Override
    public void onBindViewHolder(RoomMembersItemViewHolder viewHolder, int position) {
        Contacts contacts = members.get(position);

        if ("添加成员".equals(contacts.getDisplayname())) {
            viewHolder.setCursor(new Cursor(Cursor.HAND_CURSOR));
            String name = contacts.getDisplayname();
            viewHolder.roomName.setText(name);
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/image/add_member.png"));
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            viewHolder.avatar.setIcon(imageIcon);

            viewHolder.addMouseListener(new AbstractMouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //System.out.println("添加/刪除用戶");
                    //selectAndAddRoomMember();
                    if (addMemberButtonMouseListener != null) {
                        addMemberButtonMouseListener.mouseClicked(e);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    viewHolder.setBackground(Colors.ITEM_SELECTED_LIGHT);
                    super.mouseEntered(e);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    viewHolder.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);

                }
            });
        } else if ("删除成员".equals(contacts.getDisplayname())) {
            viewHolder.setCursor(new Cursor(Cursor.HAND_CURSOR));
            String name = contacts.getDisplayname();
            viewHolder.roomName.setText(name);
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/image/delete_member.png"));
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            viewHolder.avatar.setIcon(imageIcon);

            viewHolder.addMouseListener(new AbstractMouseListener() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    viewHolder.setBackground(Colors.ITEM_SELECTED_LIGHT);
                    super.mouseEntered(e);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    viewHolder.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (removeMemberButtonMouseListener != null) {
                        removeMemberButtonMouseListener.mouseClicked(e);
                    }
                }
            });
        } else {
            //ImageIcon imageIcon = new ImageIcon();
            //imageIcon.setImage(AvatarUtil.createOrLoadMemberAvatar(RoomMembersPanel.getContext().getRoomId(),userName).getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            //viewHolder.avatar.setIcon(imageIcon);
            String userName = contacts.getUsername();
            String name = ContactsTools.getMemberDisplayNameOfGroup(contacts,userName);
            if (contacts.getAvatar() != null){
                ImageIcon icon = new ImageIcon();
                icon.setImage(contacts.getAvatar());
                viewHolder.avatar.setIcon(icon);
            }
            viewHolder.roomName.setText(name);
            UserInfoPopup userInfoPopup = new UserInfoPopup(contacts);

            if (!name.equals(Core.getNickName())) {
                viewHolder.addMouseListener(new AbstractMouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (selectedViewHolder != null){
                            selectedViewHolder.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
                        }
                        selectedViewHolder = viewHolder;
                        viewHolder.setBackground(Colors.ITEM_SELECTED_LIGHT);

                        // 弹出用户信息面板
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            userInfoPopup.show(e.getComponent(), e.getX(), e.getY());
                        }


                        for (RoomMembersItemViewHolder holder : viewHolders) {
                            if (holder != viewHolder) {
                                holder.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
                            }
                        }

                    }
                });
            }
        }

    }


    @Override
    public int getCount() {
        return members.size();
    }

    public void setAddMemberButtonMouseListener(MouseAdapter addMemberButtonMouseListener) {
        this.addMemberButtonMouseListener = addMemberButtonMouseListener;
    }

    public void setRemoveMemberButtonMouseListener(MouseAdapter removeMemberButtonMouseListener) {
        this.removeMemberButtonMouseListener = removeMemberButtonMouseListener;
    }
}
