package ru.netology.web.test;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import lombok.val;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MoneyTransferTest {
    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        Configuration.browser = "chrome";
        Configuration.timeout = 12000;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }
    @Test
    void shouldTransferMoneyFirstToSecond() {
      //  open("http://localhost:9999");
        String transfer = "1550";
        val dashboardPage = new DashboardPage();
        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.secondCardButton();
        val infoCard = DataHelper.CardNumber.getFirstCardNumber();
        transferPage.transferForm(transfer, infoCard);
        assertEquals(balanceFirstCard - Integer.parseInt(transfer), dashboardPage.
                getFirstCardBalance());
        assertEquals(balanceSecondCard + Integer.parseInt(transfer), dashboardPage.
                getSecondCardBalance());
    }

    @Test
    void shouldTransferMoneySecondToFirst() {
        String transfer = "999";
        val dashboardPage = new DashboardPage();
        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.firstCardButton();
        val infoCard = DataHelper.CardNumber.getSecondCardNumber();
        transferPage.transferForm(transfer, infoCard);
        assertEquals(balanceFirstCard + Integer.parseInt(transfer), dashboardPage.
                getFirstCardBalance());
        assertEquals(balanceSecondCard - Integer.parseInt(transfer), dashboardPage.
                getSecondCardBalance());
    }

    @Test
    void shouldTransferMoneyMoreBalance() {
       // open("http://localhost:9999");
        String transfer = "10000";
        val dashboardPage = new DashboardPage();
        val transferPage = dashboardPage.secondCardButton();
        val infoCard = DataHelper.CardNumber.getFirstCardNumber();
        transferPage.transferForm(transfer, infoCard);
        transferPage.getError();
    }
}
