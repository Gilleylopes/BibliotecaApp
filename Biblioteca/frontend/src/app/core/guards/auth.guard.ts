import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/services/auth.service';

export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthService) as AuthService;
  const router = inject(Router);

  if (auth.isAutenticado()) {
    return true;
  }

  router.navigate(['/auth/login']);
  return false;
};
