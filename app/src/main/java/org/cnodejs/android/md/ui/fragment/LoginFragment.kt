package org.cnodejs.android.md.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import org.cnodejs.android.md.R
import org.cnodejs.android.md.databinding.FragmentLoginBinding
import org.cnodejs.android.md.ui.dialog.HowToGetAccessTokenTipDialog
import org.cnodejs.android.md.ui.listener.NavBackOnClickListener
import org.cnodejs.android.md.util.FormatUtils
import org.cnodejs.android.md.util.Navigator
import org.cnodejs.android.md.vm.LoginViewModel

class LoginFragment : BaseFragment() {
    companion object {
        fun open(navigator: Navigator) {
            navigator.push(R.id.fragment_login, isSingleTop = true)
        }
    }

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        observeViewModel(loginViewModel)

        loginViewModel.loginedEvent.observe(viewLifecycleOwner) {
            showToast(R.string.login_success)
            navigator.back()
        }

        loginViewModel.errorMessageEvent.observe(viewLifecycleOwner) {
            it?.let { message ->
                binding.edtLayoutAccessToken.error = message
            }
        }

        binding.toolbar.setNavigationOnClickListener(NavBackOnClickListener(navigator))

        binding.edtAccessToken.addTextChangedListener {
            binding.edtLayoutAccessToken.error = null
        }

        binding.btnLogin.setOnClickListener {
            val accessToken = (binding.edtAccessToken.text ?: "").trim().toString()
            if (accessToken.isBlank()) {
                binding.edtLayoutAccessToken.error = getString(R.string.please_input_access_token)
                return@setOnClickListener
            }
            if (!FormatUtils.isAccessToken(accessToken)) {
                binding.edtLayoutAccessToken.error = getString(R.string.access_token_format_error)
                return@setOnClickListener
            }
            loginViewModel.login(accessToken)
        }

        binding.btnLoginByQrCode.setOnClickListener {
            // TODO
        }

        binding.btnLoginByGithub.setOnClickListener {
            // TODO
        }

        binding.btnLoginTip.setOnClickListener {
            HowToGetAccessTokenTipDialog.show(childFragmentManager)
        }

        return binding.root
    }
}
