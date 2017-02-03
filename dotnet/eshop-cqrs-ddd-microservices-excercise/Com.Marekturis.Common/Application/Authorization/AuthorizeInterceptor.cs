using System;
using System.Linq;
using System.Reflection;
using Castle.DynamicProxy;

namespace Com.Marekturis.Common.Application.Authorization
{
    public class AuthorizeInterceptor : IInterceptor
    {
        private Authorizator authorizator;
        private IInvocation invocation;
        private string roleName;

        public AuthorizeInterceptor(Authorizator authorizator)
        {
            this.authorizator = authorizator;
        }

        public void Intercept(IInvocation invocation)
        {
            if (!HasAuthorizeAttribute(invocation))
            {
                invocation.Proceed();
                return;
            }
            this.invocation = invocation;
            InitializeRoleName();

            Authorize();
        }

        private bool HasAuthorizeAttribute(IInvocation invocation)
        {
            return invocation.MethodInvocationTarget.IsDefined(typeof(AuthorizeAttribute), true);
        }

        private void InitializeRoleName()
        {
            var attribute = (AuthorizeAttribute) invocation.MethodInvocationTarget.GetCustomAttributes(typeof(AuthorizeAttribute), true).First();
            roleName = attribute.RoleName;
        }

        private void Authorize()
        {
            var authorizbale = GetAuthorizableParameter();
            if (!authorizator.CanBeAuthorized(authorizbale.ExecutorToken, roleName))
            {
                throw new AuthorizationException("User with given token is not allowed to perform given action");
            }

            invocation.Proceed();
        }

        private Authorizable GetAuthorizableParameter()
        {
            var arguments = invocation.Arguments;
            foreach (var argument in arguments)
            {
                var authorizable = argument as Authorizable;
                if (authorizable != null)
                {
                    return authorizable;
                }
            }

            throw new InvalidOperationException("Method which is supposed to be authorized has no Authorizable attribute");
        }
    }
}
