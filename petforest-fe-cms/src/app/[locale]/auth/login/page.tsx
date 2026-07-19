'use client';
import { LoginBanner } from './components/LoginBanner';
import { LoginForm } from './components/LoginForm';

const LoginPage = () => {
  return (
    <form>
      <div className='grid grid-cols-1 lg:grid-cols-2 min-h-screen bg-white'>
        <LoginForm />
        <LoginBanner />
      </div>
    </form>
  );
};

export default LoginPage;
