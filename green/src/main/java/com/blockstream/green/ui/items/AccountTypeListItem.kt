package com.blockstream.green.ui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import com.blockstream.gdk.data.AccountType
import com.blockstream.green.R
import com.blockstream.green.databinding.ListItemAccountTypeBinding
import com.blockstream.green.gdk.descriptionRes
import com.blockstream.green.gdk.titleRes
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.mikepenz.fastadapter.ui.utils.StringHolder

class AccountTypeListItem(
    val accountType: AccountType
) :
    AbstractBindingItem<ListItemAccountTypeBinding>() {
    override val type: Int
        get() = R.id.fastadapter_account_type_item_id

    override fun bindView(binding: ListItemAccountTypeBinding, payloads: List<Any>) {
        StringHolder(accountType.titleRes())
            .applyToOrHide(binding.card.title)

        StringHolder(accountType.descriptionRes())
            .applyToOrHide(binding.card.caption)

        // Warning this setting should be reverted
        if(!isEnabled) {
            binding.card.disable()
        }
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ListItemAccountTypeBinding {
        return ListItemAccountTypeBinding.inflate(inflater, parent, false)
    }
}