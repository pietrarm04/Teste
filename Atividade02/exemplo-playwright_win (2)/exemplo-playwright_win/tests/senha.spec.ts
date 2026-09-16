import { test, expect } from '@playwright/test';

test.describe('senha funcional', () => {

  test('cadastra senha válida', async ({ page }) => {
    await page.goto('/senha');

    await page.getByLabel('Nova senha').fill('Senha123');
    await page.getByLabel('Confirmar senha').fill('Senha123');

    await page.getByRole('button', { name: 'Cadastrar senha' }).click();

    await expect(page.getByRole('status')).toHaveText('Senha cadastrada');
  });


  test('aceita senha com exatamente 8 caracteres - limite mínimo', async ({ page }) => {
    await page.goto('/senha');

    await page.getByLabel('Nova senha').fill('Senha123');
    await page.getByLabel('Confirmar senha').fill('Senha123');

    await page.getByRole('button', { name: 'Cadastrar senha' }).click();

    await expect(page.getByRole('status')).toHaveText('Senha cadastrada');
  });


  test('rejeita senha com 7 caracteres - abaixo do mínimo', async ({ page }) => {
    await page.goto('/senha');

    await page.getByLabel('Nova senha').fill('Senha12');
    await page.getByLabel('Confirmar senha').fill('Senha12');

    await page.getByRole('button', { name: 'Cadastrar senha' }).click();

    await expect(page.getByRole('alert')).toHaveText('Senha fora do padrão');
  });


  test('aceita senha com exatamente 20 caracteres - limite máximo', async ({ page }) => {
    await page.goto('/senha');

    const senha = 'Senha123456789012345';

    await page.getByLabel('Nova senha').fill(senha);
    await page.getByLabel('Confirmar senha').fill(senha);

    await page.getByRole('button', { name: 'Cadastrar senha' }).click();

    await expect(page.getByRole('status')).toHaveText('Senha cadastrada');
  });


  test('rejeita senha com 21 caracteres - acima do máximo', async ({ page }) => {
    await page.goto('/senha');

    const senha = 'Senha1234567890123456';

    await page.getByLabel('Nova senha').fill(senha);
    await page.getByLabel('Confirmar senha').fill(senha);

    await page.getByRole('button', { name: 'Cadastrar senha' }).click();

    await expect(page.getByRole('alert')).toHaveText('Senha fora do padrão');
  });


  test('rejeita senha sem letra maiúscula', async ({ page }) => {
    await page.goto('/senha');

    await page.getByLabel('Nova senha').fill('senha123');
    await page.getByLabel('Confirmar senha').fill('senha123');

    await page.getByRole('button', { name: 'Cadastrar senha' }).click();

    await expect(page.getByRole('alert')).toHaveText('Senha fora do padrão');
  });


  test('rejeita senha sem letra minúscula', async ({ page }) => {
    await page.goto('/senha');

    await page.getByLabel('Nova senha').fill('SENHA123');
    await page.getByLabel('Confirmar senha').fill('SENHA123');

    await page.getByRole('button', { name: 'Cadastrar senha' }).click();

    await expect(page.getByRole('alert')).toHaveText('Senha fora do padrão');
  });


  test('rejeita senha sem número', async ({ page }) => {
    await page.goto('/senha');

    await page.getByLabel('Nova senha').fill('SenhaTeste');
    await page.getByLabel('Confirmar senha').fill('SenhaTeste');

    await page.getByRole('button', { name: 'Cadastrar senha' }).click();

    await expect(page.getByRole('alert')).toHaveText('Senha fora do padrão');
  });


  test('rejeita senha contendo espaço', async ({ page }) => {
    await page.goto('/senha');

    await page.getByLabel('Nova senha').fill('Senha 123');
    await page.getByLabel('Confirmar senha').fill('Senha 123');

    await page.getByRole('button', { name: 'Cadastrar senha' }).click();

    await expect(page.getByRole('alert')).toHaveText('Senha fora do padrão');
  });


  test('rejeita quando senha e confirmação são diferentes', async ({ page }) => {
    await page.goto('/senha');

    await page.getByLabel('Nova senha').fill('Senha123');
    await page.getByLabel('Confirmar senha').fill('Senha456');

    await page.getByRole('button', { name: 'Cadastrar senha' }).click();

    await expect(page.getByRole('alert')).toHaveText('As senhas não coincidem');
  });

});