import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'paiementApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'rapport',
    data: { pageTitle: 'paiementApp.rapport.home.title' },
    loadChildren: () => import('./rapport/rapport.routes'),
  },
  {
    path: 'transaction',
    data: { pageTitle: 'paiementApp.transaction.home.title' },
    loadChildren: () => import('./transaction/transaction.routes'),
  },
  {
    path: 'client',
    data: { pageTitle: 'paiementApp.client.home.title' },
    loadChildren: () => import('./client/client.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
