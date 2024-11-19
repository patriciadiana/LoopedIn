namespace backend.Service
{
    public class AuthService
    {
        private string token;
        public void setToken(string token)
        {
            this.token = token;
        }
        public string getToken()
        {
            return this.token;
        }
    }
}
