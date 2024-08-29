import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Transaction e2e test', () => {
  const transactionPageUrl = '/transaction';
  const transactionPageUrlPattern = new RegExp('/transaction(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const transactionSample = {};

  let transaction;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/transactions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/transactions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/transactions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (transaction) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/transactions/${transaction.id}`,
      }).then(() => {
        transaction = undefined;
      });
    }
  });

  it('Transactions menu should load Transactions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('transaction');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Transaction').should('exist');
    cy.url().should('match', transactionPageUrlPattern);
  });

  describe('Transaction page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(transactionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Transaction page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/transaction/new$'));
        cy.getEntityCreateUpdateHeading('Transaction');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/transactions',
          body: transactionSample,
        }).then(({ body }) => {
          transaction = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/transactions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [transaction],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(transactionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Transaction page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('transaction');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionPageUrlPattern);
      });

      it('edit button click should load edit Transaction page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Transaction');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionPageUrlPattern);
      });

      it('edit button click should load edit Transaction page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Transaction');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionPageUrlPattern);
      });

      it('last delete button click should delete instance of Transaction', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('transaction').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionPageUrlPattern);

        transaction = undefined;
      });
    });
  });

  describe('new Transaction page', () => {
    beforeEach(() => {
      cy.visit(`${transactionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Transaction');
    });

    it('should create an instance of Transaction', () => {
      cy.get(`[data-cy="userID"]`).type('patientèle quoique nier');
      cy.get(`[data-cy="userID"]`).should('have.value', 'patientèle quoique nier');

      cy.get(`[data-cy="code"]`).type('à travers ha ha mesurer');
      cy.get(`[data-cy="code"]`).should('have.value', 'à travers ha ha mesurer');

      cy.get(`[data-cy="message"]`).type('tout à fait assigner restaurer');
      cy.get(`[data-cy="message"]`).should('have.value', 'tout à fait assigner restaurer');

      cy.get(`[data-cy="transactionId"]`).type('décoller neutre');
      cy.get(`[data-cy="transactionId"]`).should('have.value', 'décoller neutre');

      cy.get(`[data-cy="montant"]`).type('15298.73');
      cy.get(`[data-cy="montant"]`).should('have.value', '15298.73');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        transaction = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', transactionPageUrlPattern);
    });
  });
});
